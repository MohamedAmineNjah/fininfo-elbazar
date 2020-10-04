import React, { useEffect, EventHandler, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Icon from '@material-ui/core/Icon';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import { IRootState } from '../../shared/reducers';
import { getEntity } from './products.reducer';
import { SnackBarCustom } from './products.components';
import { IProduit } from '../../shared/model/produit.model';
import TextField from '@material-ui/core/TextField';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from '../../config/constants';
import { Grid, Badge } from '@material-ui/core';
import Rating from '@material-ui/lab/Rating';
import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart';
import Cookie from 'js-cookie';
import { editItemPanier, editPrixTotal } from '../panier/panier.reducer';

export interface IProduitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }
const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    button: {
      margin: theme.spacing(1),
      textTransform: 'capitalize'
    },
    prodInfo: {
      paddingTop: '10px'
    },
    prodRightSide: {
      padding: '15px;',
      display: 'block'
    },
    inputQte: {
      "& input": {
        padding: '6.5px 14px'
      }
    },
    fieldQte: {
      marginLeft: '5px',
      width: '20%',
      marginTop: '10px'
    },
    titleProd: {
      fontSize: '18px',
      'font-weight': '500',
      color: '#464545e8',

    },
    badge: {
      position: 'relative',
      top: '100px',
      left: '-18%',
    },
    media: {
      padding: '10%',
      width: '100%',
      margin: 'auto',
      cursor: 'pointer'
    },
    mediaBorder: {
      borderRight: "1px solid rgba(0, 0, 0, 0.18)"
    }
  }),
);
export const ProduitDetail = (props: IProduitDetailProps) => {
  const [quantite, setQuantite] = useState(1);
  const [panier, setPanier] = useState(props.listProdPanier);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackErreur, setopenSnackErreur] = useState(false);
  const classes = useStyles();

  const { produitEntity } = props;
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
      const isPromotion = (startDate.getTime() <= currentDate.getTime()) && (currentDate.getTime() <= endDate.getTime() && product.eligible_Remise === true && product.remise > 0)
      return isPromotion
    }

  };
  const handleCloseSnack = () => {
    setopenSnack(false);
  };
  const handleCloseSnackErreur = () => {
    setopenSnackErreur(false);
  };
  useEffect(() => {
    window.scrollTo(0, 0);
    props.getEntity(props.match.params.id);
  }, []);
  const handleChangeQte = (event) => {

    const value = event.target.value

    if (event.target.value > produitEntity.quantite_vente_max) {
      event.target.value = produitEntity.quantite_vente_max;

    }
    if (parseInt(value, 10) === 0) {

      event.target.value = 1;

    }
  }
  useEffect(() => {
    const number = document.getElementById('qte');
    number.onkeydown = function (e: any) {
      if (!((e.keyCode > 95 && e.keyCode < 106)
        || (e.keyCode > 47 && e.keyCode < 58)
        || e.keyCode === 8) && (e.keyCode !== 38 && e.keyCode !== 39 && e.keyCode !== 40 && e.keyCode !== 37) || (e.keyCode === 54)) {

        return false;
      }
      if (!isNaN(e.key)) {
        if (parseInt(e.target.value + e.key, 10) > produitEntity.quantite_vente_max) {
          e.target.value = produitEntity.quantite_vente_max;
        }
        if (parseInt(e.target.value + e.key, 10) === 0) {
          console.log(e.target.value + e.key)
          e.target.value = 1;
          return false
        }
        if (parseInt(e.target.value, 10) === 0) {
          console.log(e.target.value)
          e.target.value = 1;
          return false
        }
      }
      if (e.target.value.length === 1 && e.key === "Backspace") {
        e.target.value = 1;
        return false
      }
    }
  }, [produitEntity]);





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
        if (((parseInt(item.qte, 10) + parseInt(qte, 10)) <= item.quantite_vente_max)) {
          item.qte = parseInt(item.qte, 10) + parseInt(qte, 10);
          founded = true;
          setopenSnack(true);
        } else {
          item.qte = parseInt(item.quantite_vente_max, 10);
          founded = true;
          setopenSnackErreur(true);
        }
      }
    });
    if (!founded) {
      const prodToAdd = {
        ...produit,
        souscategory: Cookie.get("nomSubCat"),
        qte: parseInt(qte, 10)
      }
      arr = [
        ...panier,
        prodToAdd
      ]
      setopenSnack(true);
    }
    setPanier(arr)
    props.editItemPanier(arr);
    calculeTotalPrix(arr);// adel+ena
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = {}
    for (let i = 0; i < event.target.length - 1; i++) {
      if (event.target[i].name) {
        data[event.target[i].name] = event.target[i].value;
      }
    }
    addItemPanier(produitEntity, data["quantite"])

  }
  return (
    <div>
      <Link to={Cookie.get("idSubCat") ? `/products/${Cookie.get("idSubCat")}` : `/`}>
        <Button size="small" className={classes.button} startIcon={<ArrowBackIosIcon />}>
          Retour
      </Button>
      </Link>

      <Grid container justify="center">
        <Grid item container md={10}>
          <Grid className={classes.mediaBorder} item md={4}>

            <img className={classes.media} src={`media/${produitEntity.image_url}`} alt="" />

          </Grid>
          <Grid container className={classes.prodRightSide} item md={6}>
            <Grid item xs={12} className={classes.prodInfo}>
              <span className={classes.titleProd}>Etat : <span style={{ 'color': !produitEntity.hors_stock ? '#02b302' : 'red' }}>{!produitEntity.hors_stock ? 'Disponible' : ' Indisponible'}</span>  </span>
            </Grid>
            <Grid item xs={12} className={classes.prodInfo}>
              <span className={classes.titleProd}>Nom du produit : <span >{produitEntity.nom}</span>  </span>
            </Grid>
            <Grid item xs={12} className={classes.prodInfo} >
              <span className={classes.titleProd}>Prix :
                    {IsPromo(produitEntity) ? (
                  <React.Fragment>
                    <span className="" style={{ "marginRight": "2%", "textDecoration": "line-through", "color": "#adb5bd" }}>{" " + produitEntity.prix_ttc + " DT"}</span>
                    <span className="red" >{(produitEntity.prix_ttc - ((produitEntity.prix_ttc * produitEntity.remise) / 100)).toFixed(3) + " DT"}</span>

                  </React.Fragment>

                ) :
                  (

                    <span className="red" >{produitEntity.prix_ttc ? produitEntity.prix_ttc.toFixed(3) + " DT" : null}</span>

                  )
                }
              </span>
            </Grid>
            <Grid item xs={12} className={classes.prodInfo}>
              <span className={classes.titleProd}>Marque : <span style={{ 'color': '#5f6061e6' }} >{produitEntity.marque}</span>  </span>

            </Grid>
            <Grid item xs={12} style={{ 'display': !produitEntity.hors_stock ? 'block' : 'none' }} className={classes.prodInfo}>
              <form onSubmit={handleSubmit}>
                <span className={classes.titleProd}>
                  Quantité :
                    <TextField
                    id="qte"
                    defaultValue={quantite}
                    name="quantite"
                    onChange={handleChangeQte}
                    className={classes.fieldQte}
                    InputProps={{
                      className: classes.inputQte, inputProps: {
                        max: produitEntity.quantite_vente_max, min: 1
                      }
                    }}
                    type="number"
                    variant="outlined"
                  />
                </span>
                <Button
                  type="submit"
                  variant="contained"
                  color='secondary'
                  className={classes.button}
                  startIcon={<AddShoppingCartIcon />}
                >Ajouter</Button>
              </form>

            </Grid>
            <Grid item xs={12} className={classes.prodInfo}>
              <Rating name="pristine" value={3} />
            </Grid>
            <Grid item xs={12} className={classes.prodInfo}>
              <span className={classes.titleProd}>Description : <span style={{ 'color': '#5f6061e6' }} >{produitEntity.description}</span>  </span>

            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <br />
      <SnackBarCustom type={"success"} textMessage={"Votre produit a été ajouté"} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
      <SnackBarCustom type={"error"} textMessage={"quantité maximum atteint"} isSnackOpen={openSnackErreur} handleClose={handleCloseSnackErreur} />
    </div>
  );
};

const mapStateToProps = ({ produit, panier }: IRootState) => ({
  produitEntity: produit.entity,
  idSubCategory: produit.idSubCategory,
  listProdPanier: panier.listProduitsPanier
});

const mapDispatchToProps = {
  getEntity, editItemPanier, editPrixTotal
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitDetail);
