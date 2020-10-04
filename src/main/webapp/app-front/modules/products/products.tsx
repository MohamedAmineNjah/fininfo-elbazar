import './product.scss';
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { getSortState } from 'react-jhipster';
import { IRootState } from '../../shared/reducers';
import { getSearchEntities, getEntities } from './products.reducer';
import { editItemPanier, editPrixTotal } from '../panier/panier.reducer';
import { ITEMS_PER_PAGE } from '../../shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from '../../shared/util/entity-utils';
import { ProductItem, SnackBarCustom } from './products.components';
import { Grid, Breadcrumbs, Typography } from '@material-ui/core';
import Pagination from '@material-ui/lab/Pagination';
import { makeStyles, fade, createStyles } from '@material-ui/core/styles';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import Cookie from 'js-cookie';
export interface IProduitProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      margin: "2%",
      '& > *': {
        marginTop: theme.spacing(2),
      },

    },
    search: {
      borderBottom: '2px solid #eeeeee',
      position: 'relative',
      borderRadius: theme.shape.borderRadius,
      backgroundColor: fade(theme.palette.common.white, 0.15),
      '&:hover': {
        backgroundColor: fade(theme.palette.common.white, 0.25),
      },
      marginRight: theme.spacing(2),
      marginLeft: 0,
      width: '100%',
      [theme.breakpoints.up('sm')]: {
        marginLeft: theme.spacing(3),
        width: 'auto',
      },
    },
    searchIcon: {
      padding: theme.spacing(0, 2),
      height: '100%',
      position: 'absolute',
      pointerEvents: 'none',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
    },
    pagination: {
      marginTop: theme.spacing(2),
      width: "100%",
      margin: "0"
    },
    inputRoot: {
      color: 'inherit',
    },
    inputInput: {
      padding: theme.spacing(1, 1, 1, 0),
      transition: theme.transitions.create('width'),
      width: '20ch',

    },
  }),
);

export const Produit = (props: IProduitProps) => {
  const [search, setSearch] = useState('');
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackErreur, setopenSnackErreur] = useState(false);
  const [panier, setPanier] = useState(props.listProdPanier);
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const classes = useStyles();
  const { produitList, loading, totalPages } = props;
  const handleCloseSnack = () => {
    setopenSnack(false);
  };
  const handleCloseSnackErreur = () => {
    setopenSnackErreur(false);
  };
  const sortEntities = () => {
    props.getEntities(parseInt(props.match.params.id, 10), paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  const handleSearch = event => setSearch(event.target.value);
  const startSearching = () => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      props.getSearchEntities(
        Cookie.get("idSubCat"),
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };
  const clear = () => {
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    sortEntities()
  };
  useEffect(() => {
    if (!search) {

      sortEntities();
    }
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);
  useEffect(() => {
    const inputSearch = document.getElementById("inputSearch");
    inputSearch.addEventListener("keydown", function (e) {
      if (e.keyCode === 13) {
        startSearching()
      }
    });
  }, [search]);

  const handlePagination = (event: React.ChangeEvent<unknown>, value: number) => {
    setPaginationState({
      ...paginationState,
      activePage: value,
    });
  };


  const IsPromo = product => {

    if (!product.debut_promo || !product.fin_promo || !product.remise || !product.eligible_Remise || product.remise === 0) {
      return false
    } else {
      const currentDate = new Date();
      const startDate = new Date(product.debut_promo);
      const endDate = new Date(product.fin_promo);
      currentDate.setHours(0, 0, 0, 0)
      startDate.setHours(0, 0, 0, 0)
      endDate.setHours(0, 0, 0, 0)
      const isPromotion = (startDate.getTime() <= currentDate.getTime()) && (currentDate.getTime() <= endDate.getTime())

      return isPromotion
    }

  };


  const calculeTotalPrix = (listProduit) => {
    let totalPrix = 0
    listProduit.map(item => {

      if (IsPromo(item)) {
        const prixp = item.prix_ttc - ((item.prix_ttc * item.remise) / 100);
        const prixtot = (prixp * item.qte)

        totalPrix = totalPrix + prixtot
      } else {

        totalPrix = totalPrix + (item.prix_ttc * item.qte)
      }

    })
    props.editPrixTotal(totalPrix.toFixed(3) as any)
  }


  const addItemPanier = (produit, qte) => {
    let founded = false;
    let arr = [
      ...panier,
    ]

    arr.map(item => {
      if (item.id === produit.id && (item.qte + qte <= item.quantite_vente_max)) {
        item.qte = parseInt(item.qte, 10) + parseInt(qte, 10);
        founded = true;
      }
    });
    if (!founded) {
      const prodToAdd = {
        ...produit,
        souscategory: props.nameSubCat ? props.nameSubCat : Cookie.get("nomSubCat"),
        qte: parseInt(qte, 10)
      }
      arr = [
        ...panier,
        prodToAdd
      ]
    }
    setPanier(arr)
    props.editItemPanier(arr);
    calculeTotalPrix(arr);// adel+ena
  };








  return (
    <div className={classes.root}>
      <Breadcrumbs aria-label="breadcrumb">
        <Typography color="textPrimary" className="primeBreadcrumbs">{Cookie.get("nomCat")}</Typography>
        <Typography color="textPrimary" className="secondaryBreadcrumbs">{Cookie.get("nomSubCat")}</Typography>
      </Breadcrumbs>
      <Grid container direction='row-reverse' spacing={3} >

        <div className={classes.search}>
          <Grid container>
            <Grid item xs={2}>
              <button style={{ backgroundColor: 'white', border: 'none' }} onClick={startSearching}>
                <SearchIcon />
              </button>
            </Grid>
            <Grid item xs={10} >
              <InputBase
                id="inputSearch"
                placeholder="Recherchez…"
                onChange={handleSearch}
                classes={{
                  root: classes.inputRoot,
                  input: classes.inputInput,
                }}
                inputProps={{ 'aria-label': 'search' }}
              />
            </Grid>
          </Grid>


        </div>
      </Grid>
      <Grid container justify='center' spacing={3}  >
        <Grid container className={classes.pagination} spacing={2} >
          {produitList.map((produit, i) => (
            <Grid item xs={6} sm={6} md={4} lg={3} style={{ margin: "auto" }} key={produit.id} >
              <ProductItem addFunction={addItemPanier} setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} product={produit} />
            </Grid>
          ))}
        </Grid>
        <Grid item md={4} className={classes.pagination}>
          <Pagination count={props.totalPages} page={paginationState.activePage} onChange={handlePagination} color="secondary" />
        </Grid>
      </Grid>
      <SnackBarCustom type={"success"} textMessage={"Votre produit a été ajouté"} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
      <SnackBarCustom type={"error"} textMessage={"quantité maximum atteint"} isSnackOpen={openSnackErreur} handleClose={handleCloseSnackErreur} />
    </div>


  );
};

const mapStateToProps = ({ produit, panier }: IRootState) => ({
  produitList: produit.entities,
  idsubCategory: produit.idSubCategory,
  nameSubCat: produit.nameSubCategory,
  loading: produit.loading,
  totalPages: produit.totalPages,
  listProdPanier: panier.listProduitsPanier
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  editItemPanier,
  editPrixTotal,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Produit);
