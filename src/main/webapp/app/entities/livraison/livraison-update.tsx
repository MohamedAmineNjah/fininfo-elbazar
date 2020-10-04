import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getZones } from 'app/entities/zone/zone.reducer';
import { getEntity, updateEntity, createEntity, reset } from './livraison.reducer';
import { ILivraison } from 'app/shared/model/livraison.model';
import {
  Typography,
  TextField,
  FormControl,
  ButtonGroup,
  FormLabel,
  Card,
  CardHeader,
  Button,
  MenuItem,
  Select,
  NativeSelect,
} from '@material-ui/core';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { handelAutocomplete, getDefaultvalue, numberRegex, validateRequired, handleInputChange, CodeFloat, validateErrorOnField } from 'app/shared/util/entity-ui-services';
import { useStyles } from 'app/shared/util/entity-style';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';
import SaveIcon from '@material-ui/icons/Save';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import { IZone } from 'app/shared/model/zone.model';

export interface ILivraisonUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const LivraisonUpdate = (props: ILivraisonUpdateProps) => {

  const [isNew, setisNew] = useState(!props.match.params || !props.match.params.id);
  const [listzones, setlistzones] = useState<IZone[]>([]);
  const [valeurs, setvaleurs] = useState<ILivraison>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);

  const [openSnackMessage, setopenSnackMessage] = useState('');

  const [regexValues, setregexValues] = useState({ 
    valeurMin:  {isOnError : false },
    valeurMax:  {isOnError : false },
    frais:  {isOnError : false },
    date:  {isOnError : false },
  });

  const requiredValues = [
    { field: 'valeurMin', fieldName: 'Valeur minimum de la commande' },
    { field: 'valeurMax', fieldName: 'Valeur maximum de la commande' },
    { field: 'frais', fieldName: 'Frais de la livraison' },
    { field: 'date', fieldName: 'Délai de la livraison' },
    { field: 'categorieClient', fieldName: 'categorieClient' },
    { field: 'zoneId', fieldName: 'Zone Id' },
    { field: 'zoneNom', fieldName: 'Zone Nom' },
  ];

  const { livraisonEntity, zones } = props;

  const handleClose = () => {
    props.history.push('/livraison');
  };

  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  const classes = useStyles();

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getZones();
  }, []);

  useEffect(() => {
    if (props.zones) {
      setlistzones([...props.zones]);
    }
  }, [props.zones]);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) { setvaleurs({}); }
      else {
        setvaleurs(props.livraisonEntity);
      }
    }
  }, [props.loading]);



  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);


  const saveEntity = () => {
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) && !validateErrorOnField(regexValues)) {
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
                    Fiche formules de livraison {valeurs ? valeurs.id : ''}
                  </Typography>
                ) : (
                    <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                      Fiche nouvelle formule de livraison
                    </Typography>
                  )}
              </Col>
              <Col md="3">
                <ButtonGroup>
                  <Button id="cancel-save" onClick={clearValeurs} variant="contained" color="primary" className={classes.button} startIcon={<ArrowBackIcon />}>
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
                  <CardHeader title="Livraison" subheader="Paramètres formule de livraison" />

                  <Row>
                    <Col md="6">
                      <form className={classes.root}>
                        <FormControl component="fieldset" className={classes.formControl}>
                          <FormLabel component="legend">Profile client</FormLabel>
                          <NativeSelect
                            id="livraison-categorieclient"
                            className={classes.selectEmpty}
                            value={valeurs ? valeurs.categorieClient : ''}
                            onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                            name="categorieClient"
                            required
                            style={{ width: 170 }}
                          >
                            <option value=''>
                              Sélectionnez
                          </option>
                            <option value={'Silver'}>Silver</option>
                            <option value={'Gold'}>Gold</option>
                            <option value={'Platinium'}>Platinium</option>
                          </NativeSelect>

                        </FormControl>

                        <FormControl className={classes.formControl}>
                          <FormLabel component="legend">Zones</FormLabel>
                          {listzones && listzones.length > 0 ? (

                            <Autocomplete
                              id="livraison-zone"
                              options={listzones}
                              value={valeurs.zoneId ? getDefaultvalue(listzones, valeurs.zoneId) : ''}
                              getOptionLabel={option => option.nom}
                              onChange={(event, value) => handelAutocomplete(event, zones, value, valeurs, setvaleurs, 'zoneId', 'zoneNom')}
                              renderInput={params => <TextField {...params} name="zones" label="Sélectionnez la zone *" />}
                              style={{ width: 250 }}
                            />) : null}
                        </FormControl>
                         <TextValidator
                          id="livraison-valeurMin"
                          label="Valeur minimum de la commande en TND"
                          name="valeurMin"
                          values={{ data: valeurs, field: 'valeurMin', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues}}
                          regx={CodeFloat}
                          required
                          style={{ width: 350 , paddingBottom: 10}}
                        />
                        <TextValidator
                          id="livraison-valeurMax"
                          label="Valeur maximum de la commande en TND"
                          name="valeurMax"
                          values={{ data: valeurs, field: 'valeurMax', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                          regx={CodeFloat}
                          required
                          style={{ width: 350 , paddingBottom: 10}}
                        />
                      </form>
                    </Col>
                  </Row>
                </Card>
              </Col>

              <Col>
                <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                  <CardHeader title="Valeurs à appliquer" subheader="Frais et Délais de Livraison" />

                  <Row>
                    <Col md="6">
                      <form className={classes.root}>
                        {' '}
                        <TextValidator
                          id="livraison-frais"
                          label="Frais de la livraison"
                          name="frais"
                          values={{ data: valeurs, field: 'frais', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                          regx={CodeFloat}
                          required
                        />{' '}
                        <TextValidator
                          id="dateLabel"
                          label="Délai de livraison"
                          name="date"
                          values={{ data: valeurs, field: 'date', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                          regx={numberRegex}
                          required
                        />
                      </form>
                    </Col>
                  </Row>
                </Card>
              </Col>
            </Row>{' '}
          </div>
        )}
           <i style={{ color: 'grey' }}>* Champs obligatoires</i>
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  zones: storeState.zone.entities,
  livraisonEntity: storeState.livraison.entity,
  loading: storeState.livraison.loading,
  updating: storeState.livraison.updating,
  updateSuccess: storeState.livraison.updateSuccess,
});

const mapDispatchToProps = {
  getZones,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LivraisonUpdate);
