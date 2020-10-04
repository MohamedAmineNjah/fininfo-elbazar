import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './commande.reducer';
import { getEntities } from '../info-societe/info-societe.reducer';

import { Alert } from '@material-ui/lab';

import { ICommandeDetail } from 'app/shared/model/commande-detail.model';
import { Grid } from '@material-ui/core';
import { IInfoSociete } from 'app/shared/model/info-societe.model';
import { useStyles } from 'app/shared/util/entity-style';
import moment from 'moment';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

export interface ICommandeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeDetail = (props: ICommandeDetailProps) => {
  const [detailCommande, setdetailCommande] = useState(null);
  const [infoSociete, setinfoSociete] = useState(null);
  const classes = useStyles();
  const [totalCommande, setTotalCommande] = useState('');

  const commandeId = props.match.params.id;
  const apiUrl = `api/commandes/client`;
  const requestUrl = `${apiUrl}/${commandeId}`;
  const apiUrl1 = 'api/info-societes';

  useEffect(() => {
    props.getEntity(props.match.params.id);
    props.getEntities();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get<ICommandeDetail>(requestUrl);

      setdetailCommande(result.data);
    };

    fetchData();
  }, []);
  const { match, isAdmin } = props;

  const { InfoSocieteEntity } = props;

  const { commandeEntity } = props;

  const imprimerPage = () => {
    document.getElementById('footer').style.display = 'none';
    document.getElementById('footerBon').style.display = 'block';
    document.getElementById('impression').style.display = 'none';
    document.getElementById('back').style.display = 'none';

    window.print();

    document.getElementById('impression').style.display = 'inline-block';
    document.getElementById('back').style.display = 'inline-block';
    document.getElementById('footer').style.display = 'block';
    document.getElementById('footerBon').style.display = 'none';
  };
  useEffect(() => {
    if (commandeEntity.totTTC) {
      setTotalCommande(commandeEntity.totTTC.toFixed(3));
    }
  }, [commandeEntity]);
  const getCommandeAdresse = commande => {
    return commande.adresse + ' ' + commande.localite + ' ' + commande.ville + ', ' + commande.gouvernorat;
  };
  // le champ editable 'Total TTC'
  const handleBlur = event => {};
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTotalCommande(event.target.value);
  };

  return (
    <div>
      <Row>
        <Grid container xs={12} sm={12} item spacing={3} direction="row" justify="space-between">
          {' '}
          <Grid item sm={7} style={{ padding: '2rem' }}>
            {commandeEntity.statut === 'Livraison' ? (
              <h2 style={{ padding: '.5rem', color: '#555' }}>
                Bon De Livraison <b>{commandeEntity.reference}</b>
              </h2>
            ) : (
              <h2 style={{ padding: '.5rem', color: '#555' }}>
                Commande <b>{commandeEntity.reference}</b>
              </h2>
            )}

            <Table>
              <thead>
                <th id="nom"> Nom Client</th>
                <th id="prenom">Prénom</th>
                <th id="dateLivraison">Date de Livraison</th>
                <th id="telephone">Téléphone </th>
                <th id="adresse">Adresse </th>
              </thead>
              <tbody>
                <td>{detailCommande ? detailCommande.commandeDTO.nom : ''}</td>
                <td>{detailCommande ? detailCommande.commandeDTO.prenom : ''}</td>
                <td>{commandeEntity ? moment(commandeEntity.dateLivraison).format('DD-MM-YYYY') : ''}</td>
                <td>{detailCommande ? detailCommande.commandeDTO.mobile : ''}</td>
                <td>{detailCommande ? getCommandeAdresse(detailCommande.commandeDTO) : ''}</td>
              </tbody>
            </Table>
          </Grid>
          <Grid container sm={3} justify="flex-end" style={{ padding: '1rem' }}>
            {' '}
            <Grid item spacing={3}>
              <Grid item>
                <img src="content/images/elbazar1.png" width="100px" style={{ marginBottom: '1rem', marginLeft: '2.5rem' }} />
              </Grid>
              {/* <Grid container sm={12} style={{ backgroundColor: 'red' }}>
                <Grid item style={{ marginRight: '2rem' }}>
                  <tbody>
                    <tr>
                      <b>{InfoSocieteEntity[0] ? 'MF' : ''}</b>

                      <td>{InfoSocieteEntity[0] ? InfoSocieteEntity[0].matriculeFiscal : ''}</td>
                    </tr>
                    <tr>
                      <b>{InfoSocieteEntity[0] ? 'Adresse' : ''}</b> &nbsp;
                      <td>{InfoSocieteEntity[0] ? InfoSocieteEntity[0].adresse : ''}</td>
                    </tr>
                  </tbody>
                </Grid>
              </Grid> */}
            </Grid>
          </Grid>
        </Grid>

        <Col md="8"></Col>
        <Col md="12">
          <h2 style={{ padding: '.5rem', color: '#555' }}>Détails du Commande</h2>
          {detailCommande && detailCommande.commandeLignesDTO.length > 0 ? (
            <Table>
              <thead>
                <th id="refProduitReference">Référence Produit</th>
                <th id="nomProduit">Produit</th>
                <th id="prixHT">Prix Produit HT</th>
                <th id="quantite">Quantité</th>
                <th id="remise">Remise</th>
                <th id="tva">Tva</th>
                <th id="prixTTC">Total TTC </th>
              </thead>
              <tbody>
                {detailCommande.commandeLignesDTO.map((commande, i) => (
                  <tr key={`entity-${i}`}>
                    <td>{commande.refProduitReference}</td>
                    <td>{commande.nomProduit}</td>
                    <td>{parseFloat(commande.prixHT).toFixed(3) + ' DT'}</td>
                    <td>{commande.quantite}</td>
                    <td>{commande ? commande.remise + '%' : '0 %'} </td>
                    <td>{commande.tva + '%'} </td>
                    <td>{parseFloat(commande.prixTTC).toFixed(3) + ' DT'}</td>
                    {/* <td>{commande.prixHT * commande.quantite} TND </td> */}
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className={classes.root}>
              <Alert severity="info">
                <strong>Aucune Commande Disponible</strong>
              </Alert>
            </div>
          )}
        </Col>
        <Col md="4" className="offset-8">
          <h2 style={{ padding: '.5rem', color: '#555' }}>Récapitulation</h2>
          <Table>
            <tbody>
              <tr>
                <td>{'Frais de Livraison'}</td>
                <td>{commandeEntity.fraisLivraison ? commandeEntity.fraisLivraison.toFixed(3) + ' DT' : null} </td>
              </tr>
              <tr>
                <td>{'Total TTC'}</td>

                {/* {isAdmin ? (
                  <td>
                    <TextField
                      value={totalCommande}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      name="totTTC"
                      id="formatted-numberformat-input"
                    />
                  </td>
                ) : (
                  <td>{commandeEntity.totTTC ? commandeEntity.totTTC.toFixed(3) + ' DT' : null}</td>
                )} */}

                <td>{commandeEntity.totTTC ? commandeEntity.totTTC.toFixed(3) + ' DT' : null}</td>
              </tr>
              <tr>
                <td>{'Mode de paiment'}</td>
                <td>{commandeEntity.reglement}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
        <Col>
          <Button id="back" tag={Link} to="/commande" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
          </Button>
          &nbsp; &nbsp;
          <Button id="impression" name="impression" type="button" onClick={() => imprimerPage()} replace color="warning">
            <FontAwesomeIcon icon="print" /> <span className="d-none d-md-inline">Imprimer</span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ commande, infoSociete, authentication }: IRootState) => ({
  commandeEntity: commande.entity,
  InfoSocieteEntity: infoSociete.entities,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

const mapDispatchToProps = { getEntity, getEntities };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeDetail);
