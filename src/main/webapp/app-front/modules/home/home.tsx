import './home.scss';
import { Link, RouteComponentProps } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import { getSortState, ICrudGetAllAction, ICrudSearchAction, Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import {
  Carousel as CarouselStrap,
  CarouselItem,
  CarouselControl,
  CarouselIndicators,
  CarouselCaption
} from 'reactstrap';
import Flicking from "@egjs/react-flicking";
import { Fade, AutoPlay, } from "@egjs/flicking-plugins";
import { IRootState } from '../../shared/reducers';
import { getPromoProd, getFavProd, getNewProd, getListSlide, getPartenaire } from '../../shared/reducers/home.reducer';
import { Button } from 'reactstrap';
import { Typography, makeStyles, createStyles, Grid, Paper, fade, InputBase, List } from '@material-ui/core';
import Cookie from 'js-cookie';
import { editItemPanier, editPrixTotal } from '../panier/panier.reducer';
import { SnackBarCustom, ProductItem } from '../products/products.components';
import { getEntitieshome } from '../products/products.reducer';

import Carousel from 'react-elastic-carousel'

import LocalShippingIcon from '@material-ui/icons/LocalShipping';
import HighQualityIcon from '@material-ui/icons/HighQuality';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ILivraison2, defaultValue } from '../../shared/model/livraison2.model';
import { FAILURE, REQUEST, SUCCESS } from '../../shared/reducers/action-type.util';
import Axios from 'axios';
import SearchIcon from '@material-ui/icons/Search';
import { overridePaginationStateWithQueryParams } from '../../shared/util/entity-utils';
import { ITEMS_PER_PAGE } from '../../shared/util/pagination.constants';
import Pagination from '@material-ui/lab/Pagination';
import { Produit } from '../products/products';




export interface IHomeProp extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const Home = (props: IHomeProp) => {
  const [value, setValue] = React.useState(0);
  const [promoprod, setpromoprod] = React.useState(props.listpromoprod);
  const [promoprod1, setpromoprod1] = React.useState<any>();
  const [promoprod2, setpromoprod2] = React.useState<any>();

  const [newprod, setnewprod] = React.useState(props.listnewprod);
  const [favprod, setfavprod] = React.useState(props.listfavprod);
  const [panier, setPanier] = React.useState(props.listProdPanier);// hey mise a jour lil panier   .
  const [openSnack, setopenSnack] = React.useState(false);
  const [openSnackErreur, setopenSnackErreur] = React.useState(false);
  const [activeIndex, setActiveIndex] = React.useState(0);
  const [animating, setAnimating] = React.useState(false);
  const [refresh, setRefresh] = React.useState(false);
  const plugins = [new AutoPlay(4000 as any, "NEXT")];
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [search, setSearch] = useState('');


  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };
  const handleCloseSnack = () => {
    setopenSnack(false);
  };
  const handleCloseSnackErreur = () => {
    setopenSnackErreur(false);
  };
  const { listpromoprod, listnewprod, listfavprod, listSearchProduit, totalPages } = props;

  const handleSearch = event => setSearch(event.target.value);
  const startSearching = () => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      console.log(search);

      props.getEntitieshome(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };

  const handlePagination = (event: React.ChangeEvent<unknown>, value2: number) => {
    setPaginationState({
      ...paginationState,
      activePage: value2,
    });
  };
  useEffect(() => {
    window.scrollTo(0, 0);
    Cookie.remove("idSubCat");
    props.getPromoProd();
    props.getNewProd();
    props.getFavProd();
    props.getListSlide(),
      props.getPartenaire()

  }, []);
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
      if (item.id === produit.id) {
        if (((item.qte + qte) <= item.quantite_vente_max)) {
          item.qte = parseInt(item.qte, 10) + parseInt(qte, 10);
          founded = true;
        } else {
          item.qte = parseInt(item.item.quantite_vente_max, 10);
          founded = true;
        }

      }
    });
    if (!founded) {
      const prodToAdd = {
        ...produit,
        souscategory: produit.categorieNom,
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
  useEffect(() => {
    setTimeout(() => { setRefresh(!refresh) }, 200)
  }, [props.showedSidNav])
  useEffect(() => {
    if (props.listpromoprod) {
      setpromoprod(props.listpromoprod);
    }
  }, [props.listpromoprod]);



  useEffect(() => {
    const list1 = [];
    const list2 = [];
    if (props.listpromoprod) {

      {
        props.listpromoprod.map((product, i) => (
          i < 10 ? (list1[i] = product) : (list2[i - 10] = product)
          //  i >= 10 ? (list2[i] = product) : null
        ))
      }
      setpromoprod1(list1);
      setpromoprod2(list2);

      console.log("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
      console.log(list2);


    }
  }, [props.listpromoprod]);




  useEffect(() => {
    if (props.listnewprod) {
      setnewprod(props.listnewprod);
    }
  }, [props.listnewprod]);
  useEffect(() => {
    if (props.listfavprod) {
      setfavprod(props.listfavprod);
    }
  }, [props.listfavprod]);

  const useStyles = makeStyles(theme =>
    createStyles({
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
        marginTop: '5%',
        width: '100%',
        [theme.breakpoints.up('sm')]: {
          marginLeft: theme.spacing(3),
          width: 'auto',
        },
      },
      inputRoot: {
        color: 'inherit',
      },
      pagination: {
        marginTop: theme.spacing(2),
        width: "100%",
        margin: "0"
      },
      inputInput: {
        padding: theme.spacing(1, 10, 1, 0),
        transition: theme.transitions.create('width'),
        width: '100%',


      },
      input: {
        marginLeft: theme.spacing(1),
        flex: 10,
      },
      icon: {
        color: 'green',
        fontSize: '2.5rem',
      },
      title: {
        color: 'green',
      },
      carte: {
        height: '200px',
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
      },
      paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
      },
      red: {
        marginLeft: '16%',
      },
      arrow: {
        color: '#555',
      },
      style: {
        boxShadow: 'none',
      },
      root: {
        margin: '2%',
        '& > *': {
          marginTop: theme.spacing(2),
        },
      },
    })
  );








  const breakPoints = [
    { width: 1, itemsToShow: 1, itemsToScroll: 1 },
    { width: 550, itemsToShow: 2, itemsToScroll: 2 },
    { width: 850, itemsToShow: 3 },
    { width: 1150, itemsToShow: 4, itemsToScroll: 2 },
    { width: 1450, itemsToShow: 5 },
    { width: 1750, itemsToShow: 6 },
  ];

  const next = () => {
    if (animating) return;
    const nextIndex = activeIndex === props.listSliders.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(nextIndex);
  }

  const previous = () => {
    if (animating) return;
    const nextIndex = activeIndex === 0 ? props.listSliders.length - 1 : activeIndex - 1;
    setActiveIndex(nextIndex);
  }

  const goToIndex = (newIndex) => {
    if (animating) return;
    setActiveIndex(newIndex);
  }
  const slides = props.listSliders.map((item) => {
    return (
      <div key={item.nom} className="panel">
        <a key={item.nom} target="_blank" rel="noopener noreferrer" href={item.lien}>
          <img style={{ "height": "66vh", "width": "100%" }} src={'data:image/bmp;base64,' + item.image} />
        </a>
      </div>
    )
  });
  const classes = useStyles();
  const Arrow = ({ type, onClick, isEdge }) => {
    const pointer = type === "PREV" ? <FontAwesomeIcon icon="chevron-left" /> : <FontAwesomeIcon icon="chevron-right" />
    return (
      <Button onClick={onClick} className={"arrowSlide"} disabled={isEdge}>
        {pointer}
      </Button>
    )
  }
  return (
    <div>
      <Grid container justify="center" key={refresh.toString()} >
        {props.listSliders && props.listSliders.length > 0 ? (
          <Flicking
            id="flicking"
            className="flicking d-none d-sm-block"
            circular={true}
            gap={10}
            duration={500}
            plugins={plugins}
            adaptive={true}
            autoResize={true}

          >
            {slides}

          </Flicking>
        ) : null}
      </Grid>



      <Grid container direction='row-reverse' spacing={3} justify='center'>

        <div className={classes.search}>
          <Grid container>
            <Grid item xs={2}>
              <button style={{ backgroundColor: 'red', border: 'none', width: '100%' }} onClick={startSearching}>
                <SearchIcon />
              </button>
            </Grid>
            <Grid item xs={10} >
              <InputBase
                id="inputSearch"
                type="search" aria-label="Search"
                aria-owns="algolia-autocomplete-listbox-4"
                placeholder="Recherchez…"
                className={classes.input}

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



      <Grid container justify='center' spacing={1}  >
        <Grid container className={classes.pagination} spacing={2} >
          {listSearchProduit.map((produit, i) => (
            <Grid item xs={6} sm={6} md={4} lg={3} style={{ margin: "auto" }} key={produit.id} >
              <ProductItem addFunction={addItemPanier} setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} product={produit} />
            </Grid>
          ))}
        </Grid>
        <Grid item md={4} className={classes.pagination}>
          {listSearchProduit.length > 0 ?
            <Pagination count={props.totalPages} page={paginationState.activePage} onChange={handlePagination} color="secondary" style={{ marginBottom: '10%' }} />
            : null}
        </Grid>
      </Grid>





      <div id="divCheckbox" style={{ "display": (listSearchProduit.length > 0 ? "none" : "block") }}>
        <Grid container alignItems="center" justify="center" style={{ marginTop: '1%' }}>
          <Grid item container md={6} justify="center">
            <div className="snip1331">
              <h1>Produits en promo</h1>

            </div>
          </Grid>
        </Grid>

        {
          promoprod1 && promoprod1.length > 0 ? (

            <Grid>
              <Carousel style={{ "margin": "auto", "width": "98%" }} itemsToShow={4} breakPoints={breakPoints} renderArrow={Arrow} pagination={false}>
                {promoprod1.map((product, i) => (

                  <div key={i} className="item"><ProductItem setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} addFunction={addItemPanier} product={product} /></div>
                )
                )}
              </Carousel>

            </Grid>

          ) : null
        }

        {
          promoprod2 && promoprod2.length > 0 ? (

            <Grid>
              <Carousel style={{ "margin": "auto", "width": "98%" }} itemsToShow={4} breakPoints={breakPoints} renderArrow={Arrow} pagination={false}>
                {promoprod2.map((product2, j) => (

                  <div key={j} className="item"><ProductItem setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} addFunction={addItemPanier} product={product2} /></div>
                )
                )}
              </Carousel>

            </Grid>

          ) : null
        }








        <div className="sectionWrapper">
          <div>
            <div className="sectionInner">

              <Grid container justify="center" spacing={3} alignItems="center">
                <Grid item xs={12} sm={4}>
                  <Paper className={classes.carte} elevation={5}>
                    <Grid xs item>
                      <LocalShippingIcon className={classes.icon} />
                    </Grid>
                    <Grid item xs>
                      <Typography variant="h6" className={classes.title}>
                        Livraison Express
              </Typography>
                    </Grid>
                    <Grid item xs>
                      <Typography >
                        Nous livrons avec les meilleures companies de transport express en Tunisie.
              </Typography>

                    </Grid>
                  </Paper>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <Paper className={classes.carte} elevation={5}>
                    <Grid xs item>
                      <HighQualityIcon className={classes.icon} />
                    </Grid>
                    <Grid item xs>
                      {' '}
                      <Typography variant="h6" className={classes.title}>
                        Meilleure Qualité
              </Typography>
                    </Grid>
                    <Grid item xs>
                      <Typography >
                        Nous livrons avec les meilleures companies de transport express en Tunisie.
              </Typography>
                    </Grid>
                  </Paper>
                </Grid>
              </Grid>
            </div>
          </div>


        </div>

        <br />
        <Grid alignItems="center" container justify="center" style={{ marginTop: '1%' }} className="my-3">
          <Grid item container md={6} justify="center">

            <div className="orange">
              <h1>Nouveaux Produits</h1>

            </div>

          </Grid>
        </Grid>
        {
          newprod && newprod.length > 0 ? (
            <Grid>
              <Carousel style={{ "margin": "auto", "width": "98%" }} itemsToShow={4} breakPoints={breakPoints} renderArrow={Arrow} pagination={false}>
                {newprod.map((product, i) => (
                  <div key={i} className="item"><ProductItem setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} addFunction={addItemPanier} product={product} /></div>
                ))}
              </Carousel>
            </Grid>
          ) : null
        }
        <Grid alignItems="center" container justify="center" style={{ marginTop: '1%' }} className="my-3">
          <Grid item container md={6} justify="center">


            <div className='vert'>
              <h1>Produits en vedette</h1>

            </div>

          </Grid>
        </Grid>
        <div className="sectionWrapper">
          <div >
            <div className="sectionInner">


              {favprod && favprod.length > 0 ? (
                <Grid>
                  <Carousel style={{ "margin": "auto", "width": "98%" }} itemsToShow={4} breakPoints={breakPoints} renderArrow={Arrow} pagination={false}>
                    {favprod.map((product, i) => (
                      <div key={i} className="item"><ProductItem setopensnackerror={setopenSnackErreur} setopensnack={setopenSnack} listprodpanier={props.listProdPanier} addFunction={addItemPanier} product={product} /></div>
                    ))}
                  </Carousel>
                </Grid>
              ) : null}


            </div>
          </div>

        </div>

        <Grid style={{ marginTop: "5rem" }}>


          <section className="left">
            <h5 className="section-header">
              &nbsp;
  </h5>
            <div className="section-background"></div>
            <p className="section-text">
              <strong>El Bazar</strong> est à votre porte<br />
              <strong>El Bazar</strong> vend des produits tunisiens authentiques, uniques et de meilleure qualité.

Nous fournissons quotidiennment des produits tunisiens de meilleure qualité dans notre magazin
 EL BAZAR les collectons dans une seule boîte,
 préparons soigneusement l&apos;expédition et vous envoyons le plus rapide possible<br />
              <Button variant="outlined" color="danger" className="my-3">
                En savoir Plus
              </Button>
            </p>

            <div className="section-image"></div>
          </section>
        </Grid>


        <Grid alignItems="center" container justify="center" style={{ marginTop: '1%' }} className="my-3">
          <Grid className="linedesign" item container md={6} justify="center">
            <Grid item md={4} className="divlineTitle">
              <i className="linetitle">Nos partenaire </i>
            </Grid>
          </Grid>
        </Grid>
        {
          props.listPartenaire && props.listPartenaire.length > 0 ? (
            <Grid style={{ "paddingBottom": "5%" }}>
              <Carousel style={{ "margin": "auto", "width": "98%" }} itemsToShow={4} breakPoints={breakPoints} renderArrow={Arrow} pagination={false}>
                {props.listPartenaire.map((partenaire, i) => (
                  <div key={i} ><img style={{ "height": "120px" }} src={'data:image/bmp;base64,' + partenaire.image} /></div>
                ))}
              </Carousel>
            </Grid>
          ) : null
        }
      </div>
      <SnackBarCustom type={"success"} textMessage={"Votre produit a été ajouté"} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
      <SnackBarCustom type={"error"} textMessage={"quantité maximum atteint"} isSnackOpen={openSnackErreur} handleClose={handleCloseSnackErreur} />
    </div >
  );
};

const mapStateToProps = ({ producthome, panier, categories, produit }: IRootState) => ({
  listpromoprod: producthome.listpromoprod.content,
  listnewprod: producthome.listnewprod.content,
  listfavprod: producthome.listfavprod.content,
  listSliders: producthome.listSliders,
  listPartenaire: producthome.listPartenaire,
  listProdPanier: panier.listProduitsPanier,
  showedSidNav: categories.showedSidNav,
  listSearchProduit: produit.entities,
  totalPages: produit.totalPages,

});

const mapDispatchToProps = {
  getPromoProd,
  getNewProd,
  getFavProd,
  editItemPanier,
  getListSlide,
  getPartenaire,
  editPrixTotal,
  getEntitieshome,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Home);

