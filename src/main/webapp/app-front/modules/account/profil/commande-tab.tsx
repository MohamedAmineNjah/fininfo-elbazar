import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { getSearchEntities, getEntities } from './commande-tab.reducer';
import { IRootState } from '../../../shared/reducers';
import { Table, TableHead, TableRow, TableCell, TableBody, Grid, CircularProgress } from '@material-ui/core';
import DateRangeIcon from '@material-ui/icons/DateRange';
import { ICommandeDetail } from '../../../shared/model/commande-detail.model';
import { Alert } from '@material-ui/lab';
import { useStyles } from './commande-tab-style';

import './profil.scss';

export interface ICommandeProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeProfilFront = (props: any) => {
  const [detailCommande, setdetailCommande] = useState(null);

  const classes = useStyles();

  const apiUrl = `api/commandes/client`;
  const requestUrl = `${apiUrl}`;
  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get<ICommandeDetail>(requestUrl);

      setdetailCommande(result.data);
    };
    fetchData();
  }, []);
  return (
    <div>
      {detailCommande && detailCommande.length > 0 ? (
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <h3 className="commandeTitle">Mes Commandes</h3>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {detailCommande.map((Commandeitem, i) => (
              <TableRow key={`entity-${i}`}>
                <TableCell>
                  <ExpansionPanel className="noBox">
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1a-content" id="panel1a-header">
                      <div className={classes.root}>
                        <Grid container alignItems="center" justify="center"  spacing={2}>
                          <Grid className={classes.paper} item xs={12} sm={3}>
                            <b> Commande : {Commandeitem.reference} </b>
                          </Grid>
                          <Grid className={classes.paper} item xs={12} sm={3}>
                            <b>créé le : {Commandeitem.date_creation}</b>
                          </Grid>
                          <Grid item xs={12} sm={3} className={classes.paper}>
                            {' '}
                            <b style={{ color: 'red' }}> Montant à payer : {Commandeitem.tot_ttc? Commandeitem.tot_ttc.toFixed(3):""}dt </b>
                          </Grid>
                          <Grid className={classes.paper} item xs={12} sm={3}>
                            {Commandeitem.statut === 'Annulee' ? (
                              <h6>
                                <span className="badge badge-pill badge-danger">Annulée</span>
                              </h6>
                            ) : null}
                            {Commandeitem.statut === 'Livree' ? (
                              <h6>
                                <span className="badge badge-pill badge-success">Livrée</span>
                              </h6>
                            ) : null}
                            {Commandeitem.statut === 'Livraison' ? (
                              <h6>
                                <span className="badge badge-pill badge-warning">En cours</span>
                              </h6>
                            ) : null}
                            {Commandeitem.statut === 'Reservee' ? (
                              <h6>
                                <span className="badge badge-pill badge-secondary color">Commandée</span>
                              </h6>
                            ) : null}
                            {Commandeitem.statut === 'Commandee' ? (
                              <h6>
                                <span className="badge badge-pill badge-primary">Confirmée</span>
                              </h6>
                            ) : null}
                          </Grid>
                        </Grid>
                      </div>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                      {Commandeitem && Commandeitem.commande_lignes.length > 0 ? (
                        <Grid style={{ width: '100%' }}>
                          {Commandeitem.commande_lignes.map((Proditem, it) => (
                            <Grid key={`entity-${it}`} className="tab">
                              <Grid
                                container
                                direction="row"
                                justify="center"
                                alignItems="center"
                                style={{ borderBottom: 'solid 1px #bbb', padding: '1rem', display: 'flex', textAlign: 'left' }}
                              >
                                <Grid item xs={12} sm={2}>
                                  <b className="label"> Réference: </b>
                                  {Proditem.reference}
                                </Grid>
                                <Grid item xs={12} sm={5}>
                                  <b className="label"> Produit:</b>

                                  {Proditem.nom}
                                </Grid>
                                <Grid item xs={12} sm={2}>
                                  <b className="label"> {"Nombre d'articles:"}</b>
                                  {Proditem.quantite}
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                  <b className="label"> Total:</b> {Proditem.prix_ttc ? Proditem.prix_ttc.toFixed(3):""}dt
                                </Grid>
                              </Grid>
                            </Grid>
                          ))}
                        </Grid>
                      ) : (
                        <div className={classes.root}>
                          <Alert severity="info">
                            <strong>Aucune Commande Disponible</strong>
                          </Alert>{' '}
                        </div>
                      )}
                    </ExpansionPanelDetails>
                  </ExpansionPanel>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      ) : (
        <div className={classes.root}>
          {detailCommande ? (
            <Alert severity="info">
              <strong>Aucune Commande Disponible</strong>
            </Alert>
          ) : (
            <CircularProgress color="secondary" />
          )}
        </div>
      )}
    </div>
  );
};

const mapStateToProps = ({ commande }: IRootState) => ({
  CommandeList: commande.entities,
  loading: commande.loading,
  totalItems: commande.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeProfilFront);
