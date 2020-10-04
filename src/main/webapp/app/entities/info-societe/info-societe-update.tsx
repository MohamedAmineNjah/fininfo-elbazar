import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import Button from '@material-ui/core/Button';
import SaveIcon from '@material-ui/icons/Save';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import { getEntity, updateEntity, createEntity, reset } from './info-societe.reducer';
import { IInfoSociete } from 'app/shared/model/info-societe.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useStyles } from 'app/shared/util/entity-style';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import { Typography, TextField, ButtonGroup } from '@material-ui/core';
import { handleInputChange, numberRegex, RefRegex, validateRequired, CodeFloat } from 'app/shared/util/entity-ui-services';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';

export interface IInfoSocieteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InfoSocieteUpdate = (props: IInfoSocieteUpdateProps) => {
  const { infoSocieteEntity, loading, updating } = props;
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IInfoSociete>({});
  const [openSnack, setopenSnack] = useState(false);
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [regexValues, setregexValues] = useState({
    position: { isOnError: false },
  });
  const classes = useStyles();

  const requiredValues = [
    { field: 'nomSociete', fieldName: 'Nom Société ' },
    { field: 'adresse', fieldName: 'Adresse ' },
    { field: 'tel1', fieldName: 'Téléphone 1' },
    { field: 'tel1', fieldName: 'Téléphone 2' },
    { field: 'tel3', fieldName: 'Téléphone 3' },
    { field: 'email1', fieldName: 'Email1 ' },
    { field: 'email2', fieldName: 'Email2 ' },
    { field: 'valeurMinPanier', fieldName: 'ValeurMinPanier ' },
  ];

  const handleClose = () => {
    props.history.push('/info-societe');
  };
  const handleCloseSnack = () => {
    setopenSnack(false);
  };
  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    } else {
      if (isNew) {
        props.reset();
      } else {
        props.getEntity(props.match.params.id);
      }
    }
  }, []);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.infoSocieteEntity);
      }
    }
  }, [props.loading]);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = () => {
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) && !errorOnField) {
      if (isNew) {
        valeurs.id = null;
        props.createEntity(valeurs);
      } else {
        props.updateEntity(valeurs);
      }
    } else {
      setopenSnack(true);
    }
  };

  const clearValeurs = () => {
    setvaleurs({});
    handleClose();
  };

  return (
    <div>
      {props.loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          <Row>
            <Col md="9">
              {!isNew ? (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche information société {valeurs ? valeurs.id : ''}
                </Typography>
              ) : (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche nouvelle société
                </Typography>
              )}
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
              <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                <CardHeader title="Informations" subheader="Nom et Adresse du société" />

                <Row>
                  <Col md="6">
                    <form className={classes.root}>
                      <TextField
                        id="info-societe-nomSociete"
                        label="Nom Societe"
                        name="nomSociete"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.nomSociete : ''}
                        required
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-adresse"
                        label="Adresse"
                        name="adresse"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.adresse : ''}
                        required
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-matriculeFiscal"
                        label="Matricule Fiscal"
                        name="matriculeFiscal"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.matriculeFiscal : ''}
                        required
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextValidator
                        id="info-societe-valeurMinPanier"
                        label="Valeur min panier"
                        name="valeurMinPanier"
                        values={{
                          data: valeurs,
                          field: 'valeurMinPanier',
                          setter: setvaleurs,
                          errorsetter: seterrorOnField,
                          regValue: regexValues,
                          globalErrorSetter: setregexValues,
                        }}
                        required
                        regx={CodeFloat}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                    </form>
                  </Col>
                </Row>
              </Card>
            </Col>

            <Col>
              <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                <CardHeader title="Télephones" subheader="les numéros du société" />

                <Row>
                  <Col md="6">
                    <form className={classes.root}>
                      {' '}
                      <TextValidator
                        id="info-societe-tel1"
                        label="Tel 1"
                        name="tel1"
                        values={{ data: valeurs, field: 'tel1', setter: setvaleurs, errorsetter: seterrorOnField }}
                        regx={numberRegex}
                        required
                      />{' '}
                      <TextValidator
                        id="info-societe-tel2"
                        label="Tel 2"
                        name="tel2"
                        values={{ data: valeurs, field: 'tel2', setter: setvaleurs, errorsetter: seterrorOnField }}
                        regx={numberRegex}
                        required
                      />
                      <TextValidator
                        id="info-societe-tel3"
                        label="Tel 3"
                        name="tel3"
                        values={{ data: valeurs, field: 'tel3', setter: setvaleurs, errorsetter: seterrorOnField }}
                        regx={numberRegex}
                        required
                      />
                    </form>
                  </Col>
                </Row>
              </Card>
            </Col>
            <Col>
              <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                <CardHeader title="Email" subheader="Les Emails du Société" />

                <Row>
                  <Col md="6">
                    <form className={classes.root}>
                      <TextField
                        id="info-societe-email1"
                        label="Email 1"
                        name="email1"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.email1 : ''}
                        required
                      />
                    </form>
                  </Col>
                  <Col md="6">
                    <form className={classes.root}>
                      <TextField
                        id="info-societe-email2"
                        label="Email 2"
                        name="email2"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.email2 : ''}
                        required
                      />
                    </form>
                  </Col>
                </Row>
              </Card>
            </Col>
            <Col>
              <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                <CardHeader title="Réseaux sociaux" subheader="Facebook, Youtube, Instagram..." />

                <Row>
                  <Col md="6">
                    <form className={classes.root}>
                      <TextField
                        id="info-societe-facebook"
                        label="Facebook"
                        name="facebook"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.facebook : ''}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-youtube"
                        label="Youtube"
                        name="youtube"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.youtube : ''}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-instagram"
                        label="Instagram"
                        name="instagram"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.instagram : ''}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-twitter"
                        label="Twitter"
                        name="twitter"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.twitter : ''}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                      <TextField
                        id="info-societe-tiktok"
                        label="Tiktok"
                        name="tiktok"
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs ? valeurs.tiktok : ''}
                        style={{ marginBottom: '20px', width: 150 }}
                      />
                    </form>
                  </Col>
                </Row>
              </Card>
            </Col>
          </Row>
        </div>
      )}
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  infoSocieteEntity: storeState.infoSociete.entity,
  loading: storeState.infoSociete.loading,
  updating: storeState.infoSociete.updating,
  updateSuccess: storeState.infoSociete.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InfoSocieteUpdate);
