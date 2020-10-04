import './panier.scss'
import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';

import { IRootState } from '../../shared/reducers';
import LoginModal from '../../modules/login/login-modal';
import { login } from '../../shared/reducers/authentication';
import { RowProd, InfoLivraison, ConfirmerInfoAdresse, ValidationFinal, Recap } from './panier.components'
import { editItemPanier, editPrixTotal, getAdressePrincipal, getLivraisonParam, sendCommande } from './panier.reducer'
import { Button } from 'reactstrap';
import { Grid, Typography, Table, Popover, Box, TableBody, TableContainer, Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import PopupState, { bindTrigger, bindPopover } from 'material-ui-popup-state';
export interface IPanierProps extends StateProps, DispatchProps, RouteComponentProps<{}> { }

const useStyles = makeStyles({
  tableContainer: {
    maxHeight: '63vh',
    overflowY: "scroll",
    overflowX: "hidden",
  },
  table: {
    minWidth: 700,
    border: "1px solid #dee2e6",

  },
  pageTitle: {
    color: "#6c757d",
    fontWeight: "bold",
    textAlign: 'center',
    marginTop: "2%"
  },
  cardInfo: {
    padding: '5%',
    border: 'solid 1px #ccc',
    borderRadius: '.5rem'

  }
});



export const Panier = (props: IPanierProps) => {
  const [listProduits, setListProduits] = useState(props.listProdPanier);
  const [prixTotal, setPrixTotal] = useState(0);
  const [valeurMin, setValeurMin] = useState(0);
  const [step, setStep] = useState(0);
  const [showModal, setShowModal] = useState(false);
  const [adressePrincipal, setAdressePrincipal] = useState({});

  const handleLogin = (username, password, rememberMe = false) => props.login(username, password, rememberMe);
  const handleClose = () => {
    setShowModal(false);
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
    setPrixTotal(totalPrix.toFixed(3) as any);
    props.editPrixTotal(totalPrix.toFixed(3) as any)
  }
  const classes = useStyles();
  useEffect(() => {
    window.scrollTo(0, 0)
    setListProduits(props.listProdPanier)
    if (props.listProdPanier && props.listProdPanier.length > 0)
      calculeTotalPrix(listProduits)
  }, [props.listProdPanier]);
  useEffect(() => {
    if (props.isAuthenticated && props.account) {
      props.getAdressePrincipal(props.account.id)
      setShowModal(false);

    }
  }, [props.isAuthenticated, props.account]);
  useEffect(() => {
    if (props.valeurMinPanier) {
      setValeurMin(props.valeurMinPanier)

    }
  }, [props.valeurMinPanier]);
  useEffect(() => {
    if (props.adresses) {
      let array = [...props.adresses]
      array = array.filter(item => item.principale === true)
      setAdressePrincipal(array[0]);

    }
  }, [props.adresses]);
  const goNextStep = () => {
    console.log(valeurMin);
    if (prixTotal > valeurMin) {
      if (props.isAuthenticated) {
        if (listProduits && listProduits.length > 0) {
          setStep(1)
        }
      } else {
        setShowModal(true);

      }
    }
  }

  const ShowProdPanier = () => {
    if (listProduits.length > 0) {
      return (
        <React.Fragment>
          {listProduits.map((row) => (
            <RowProd key={row.nom} edititempanier={props.editItemPanier} row={row} listproduits={listProduits} setlistproduits={setListProduits} calculetotal={calculeTotalPrix} />
          ))}
        </React.Fragment>
      )
    } else {
      return (
        <Typography variant="subtitle2" style={{ "color": '#6c757d', "margin": "15%", "textAlign": "center", "fontWeight": "bold", "fontSize": "20px" }} gutterBottom>{"Votre panier est actuellement vide"}</Typography>
      )
    }
  }
  switch (step) {
    case 0:
      return (
        <div>
          <h5 className="pageTitle" >Mon panier</h5>
          <Grid container spacing={2} >
            <Grid item container md={8}  >
              <Grid item container style={{ "padding": "1.5%" }}   >
                <TableContainer className={classes.tableContainer} >
                  <Grid aria-label="customized table">

                    <ShowProdPanier />

                  </Grid>

                </TableContainer >
                <Grid container item md={12} style={{ borderTop: "1px solid #cecece", paddingTop: "1.5%" }} justify="flex-end">
                  <Grid container item xs={10} md={5}>
                    <Grid item xs={4} md={4} >
                      <Typography variant="subtitle2" style={{ "color": '#6c757d', "fontWeight": "bold", "fontSize": "20px" }} gutterBottom>{"Total :"}</Typography>
                    </Grid>
                    <Grid item xs={8} md={8}>
                      <Typography variant="subtitle2" style={{ "color": '#e21717', "fontWeight": "bold", "fontSize": "20px" }} gutterBottom>{prixTotal + " DT TTC"}</Typography>
                    </Grid>
                  </Grid>
                </Grid>
                <Grid container item style={{ borderTop: "1px solid #cecece", padding: "1%" }} justify="flex-end">
                  <Grid item xs={4} sm={3} style={{ marginTop: "1.5%" }}>
                    {prixTotal <= valeurMin ?
                      (
                        <PopupState variant="popover" popupId="demo-popup-popover">
                          {(popupState) => (
                            <div>
                              <Button onClick={goNextStep} color="success" {...bindTrigger(popupState)}> Commander</Button>
                              <Popover
                                {...bindPopover(popupState)}
                                anchorOrigin={{
                                  vertical: 'top',
                                  horizontal: 'center',
                                }}
                                transformOrigin={{
                                  vertical: 'bottom',
                                  horizontal: 'left',
                                }}
                              >
                                <Box p={2}>
                                  <Typography> {"Votre commande doit depasser " + valeurMin + " dt"} </Typography>
                                </Box>
                              </Popover>
                            </div>
                          )}
                        </PopupState>)
                      : (<Button onClick={goNextStep} color="success"  > Commander</Button>)
                    }
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12} md={4} style={{ "padding": "1.5%" }}>
              <Grid item xs={12} md={12}>
                <TableContainer className={classes.cardInfo}  >
                  <InfoLivraison />
                </TableContainer >
              </Grid>
            </Grid>
          </Grid>
          <LoginModal showModal={showModal} handleLogin={handleLogin} handleClose={handleClose} loginError={props.loginError} />
        </div>

      );
    case 1:
      return (<ConfirmerInfoAdresse getlivraisonparam={props.getLivraisonParam} adresseentity={props.adresseentity} account={props.account} setstep={setStep} setadresseprincipal={setAdressePrincipal} adresseprincipal={adressePrincipal} prixtotal={prixTotal} listproduits={listProduits} />);
    case 2:
      return (<ValidationFinal setstep={setStep} sendcommande={props.sendCommande} account={props.account} livraisonparam={props.livraisonParam} adresseprincipal={adressePrincipal} prixtotal={prixTotal} listproduits={listProduits} />);
    case 3:
      return (<Recap prixtotal={prixTotal} issended={props.isSended} commanderep={props.commandeRep} setlistproduits={setListProduits} listproduits={props.listProduitsSended} adresseprincipal={adressePrincipal} livraisonparam={props.livraisonParam} />);
    default:
      return (<div> test</div>);
  }
  LoginModal

};

const mapStateToProps = ({ panier, authentication, adresse, producthome }: IRootState) => ({
  listProdPanier: panier.listProduitsPanier,
  adresses: panier.adresses,
  livraisonParam: panier.livraisonParm,
  isAuthenticated: authentication.isAuthenticated,
  loginError: authentication.loginError,
  account: authentication.account,
  commandeRep: panier.commandeRep,
  adresseentity: adresse.entity,
  isSended: panier.isSended,
  listProduitsSended: panier.listProduitsSended,
  valeurMinPanier: producthome.valeurMinPanier
});

const mapDispatchToProps = {
  editItemPanier,
  editPrixTotal,
  login,
  getAdressePrincipal,
  getLivraisonParam,
  sendCommande,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Panier);





