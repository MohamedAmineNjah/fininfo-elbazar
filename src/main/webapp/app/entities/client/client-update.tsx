import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';

import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, getEntityAdresses, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';

import {
  Card,
  CardHeader,
  FormGroup,
  FormControlLabel,
  Switch,
  Typography,
  makeStyles,
  createStyles,
  Theme,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  Paper,
  TableBody,
  withStyles,
  ButtonGroup,
  Button,
} from '@material-ui/core';

import { handleChangeChecked } from 'app/shared/util/entity-ui-services';
import { useStylesClient } from 'app/shared/util/entity-style';
import { IAdresse } from 'app/shared/model/adresse.model';
import moment from 'moment';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientUpdate = (props: IClientUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IClient>({});
  const [adresseList, setadresseList] = useState<IAdresse[]>([]);

  const { clientEntity, adresseByClientList, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/client' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEntityAdresses(props.match.params.id);
  }, []);

  useEffect(() => {
    if (!props.loading) {
      setvaleurs(props.clientEntity);
    }
  }, [props.loading]);

  useEffect(() => {
    if (props.adresseByClientList) {
      setadresseList([...props.adresseByClientList]);
    }
  }, [props.adresseByClientList]);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = () => {
    if (isNew) {
      valeurs.id = null;
      props.createEntity(valeurs);
    } else {
      props.updateEntity(valeurs);
    }
  };

  const clearValeurs = () => {
    setvaleurs({});
    handleClose();
  };

  const classes = useStylesClient();

  const StyledTableCell = withStyles((theme: Theme) =>
    createStyles({
      head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
      },
      body: {
        fontSize: 14,
      },
    })
  )(TableCell);

  const StyledTableRow = withStyles((theme: Theme) =>
    createStyles({
      root: {
        '&:nth-of-type(odd)': {
          backgroundColor: theme.palette.action.hover,
        },
      },
    })
  )(TableRow);

  const renderPackImage = clientPack => {
    switch (clientPack) {
      case 'Silver':
        return (
          <React.Fragment>
            <div>
              <img className={classes.image} src="content/images/prizeSilver.svg" />{' '}
            </div>
          </React.Fragment>
        );
      case 'Gold':
        return (
          <React.Fragment>
            <div>
              <img className={classes.image} src="content/images/prize.svg" />{' '}
            </div>
          </React.Fragment>
        );
      case 'Platinium':
        return (
          <React.Fragment>
            <div>
              <img className={classes.image} src="content/images/premium.svg" />{' '}
            </div>
          </React.Fragment>
        );
      default:
        break;
    }
  };

  return (
    <div>
      <br />
      <Row className="justify-content-center">
        <Col md="8">
          {props.loading ? (
            <p>Loading...</p>
          ) : (
            <div>
              <Row>
                <Col md="9">
                  <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                    Consultation Client
                  </Typography>
                </Col>
                <Col md="3">
                  <ButtonGroup>
                    <Button
                      id="cancel-save"
                      onClick={clearValeurs}
                      variant="contained"
                      color="primary"
                      className={classes.button}
                      startIcon={<ArrowBackIcon />}
                    >
                      Retour
                    </Button>
                    <Button
                      id="save-entity"
                      type="submit"
                      variant="contained"
                      color="primary"
                      onClick={saveEntity}
                      className={classes.button}
                      startIcon={<SaveIcon />}
                    >
                      Enregistrer
                    </Button>
                  </ButtonGroup>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Card>
                    <CardHeader
                      action={
                        <FormGroup>
                          <FormControlLabel
                            control={
                              <Switch
                                checked={valeurs ? valeurs.etat : false}
                                onChange={e => handleChangeChecked(e, valeurs, setvaleurs)}
                                name="etat"
                                color="primary"
                              />
                            }
                            label="Activer / Désactiver"
                          />
                        </FormGroup>
                      }
                      title={'Fiche Client : ' + (valeurs.civilite === 'M' ? 'Mr' : 'Mme') + ' ' + valeurs.nom + ' ' + valeurs.prenom}
                    />
                    <Row>
                      <Col md="5">
                        <div className={classes.root}>
                          {' '}
                          {'Date de naissance : '}
                          {moment(valeurs.dateDeNaissance).format('DD-MM-YYYY')}
                        </div>
                        <div className={classes.root}>
                          {' '}
                          {'E-mail : '}
                          {valeurs.email}
                        </div>
                        <div className={classes.root}>
                          {' '}
                          {'Mobile : '}
                          {valeurs.mobile}
                        </div>
                      </Col>
                      <Col md="5">
                        <div className={classes.root}>
                          {' '}
                          {'Inscris depuis le '}
                          {moment(valeurs.inscription).format('DD-MM-YYYY')}
                        </div>
                        <div className={classes.root}>
                          {' '}
                          {'Montant total achat en TND: '}
                          {valeurs.totalAchat}
                        </div>
                        <div className={classes.root}>
                          {' '}
                          {'Réglement : '}
                          {valeurs.reglement}
                        </div>
                      </Col>
                      <Col md="2">
                        <div>{renderPackImage(valeurs.profile)}</div>
                        <div className={classes.root}>
                          {' '}
                          {valeurs.pointsFidelite}
                          {' Points'}
                        </div>
                      </Col>
                    </Row>
                  </Card>
                </Col>
              </Row>

              <br />
              <Row>
                <Col>
                  <Card>
                    <CardHeader title={'Liste des adresses'} />
                    <Row>
                      {adresseList && adresseList.length > 0 ? (
                        <TableContainer component={Paper} className={classes.tablecontainer}>
                          <Table className={classes.table} aria-label="simple table">
                            <TableHead>
                              <StyledTableRow>
                                <StyledTableCell align="center">Ligne Adresse</StyledTableCell>
                                <StyledTableCell align="right">Nom</StyledTableCell>
                                <StyledTableCell align="right">Prénom</StyledTableCell>
                                <StyledTableCell align="right">Localité</StyledTableCell>
                                <StyledTableCell align="right">Ville</StyledTableCell>
                                <StyledTableCell align="right">Gouvernorat</StyledTableCell>
                                <StyledTableCell align="right">Code Postal</StyledTableCell>
                                <StyledTableCell align="right">Zone</StyledTableCell>
                                <StyledTableCell align="right">Indication</StyledTableCell>
                                <StyledTableCell align="right">Téléphone</StyledTableCell>
                                <StyledTableCell align="center">Mobile</StyledTableCell>
                              </StyledTableRow>
                            </TableHead>
                            <TableBody>
                              {adresseList.map(adresse => (
                                <StyledTableRow key={adresse.id}>
                                  <StyledTableCell component="th" scope="row">
                                    {adresse.adresse}
                                  </StyledTableCell>
                                  <StyledTableCell align="right">{adresse.nom}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.prenom}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.localite}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.ville}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.gouvernorat}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.codePostalCodePostal}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.zoneNom}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.indication}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.telephone}</StyledTableCell>
                                  <StyledTableCell align="right">{adresse.mobile}</StyledTableCell>
                                </StyledTableRow>
                              ))}
                            </TableBody>
                          </Table>
                        </TableContainer>
                      ) : null}
                    </Row>
                  </Card>
                </Col>
              </Row>
            </div>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess,
  adresseByClientList: storeState.client.clientAdresses,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  getEntityAdresses,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientUpdate);
