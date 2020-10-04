import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import ReactDom from 'react-dom';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col, Label, Input } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IZone } from 'app/shared/model/zone.model';
import { getEntities as getZones } from 'app/entities/zone/zone.reducer';
import { getEntity, updateEntity, createEntity, reset } from './affectation-zone.reducer';
import Autocomplete from '@material-ui/lab/Autocomplete';

import { makeStyles } from '@material-ui/core/styles';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import lightGreen from '@material-ui/core/colors/lightGreen';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import blue from '@material-ui/core/colors/blue';
import {
  Typography,
  TextField,
  FormControl,
  NativeSelect,
  InputLabel,
  FormControlLabel,
  Checkbox,
  FormGroup,
  ButtonGroup,
  Grid,
  FormLabel,
} from '@material-ui/core';
import { red } from '@material-ui/core/colors';
import Switch from '@material-ui/core/Switch';
import { IAffectationZone } from 'app/shared/model/affectation-zone.model';

import { handelAutocomplete, getDefaultvalue, validateRequired } from 'app/shared/util/entity-ui-services';
import { useStyles } from 'app/shared/util/entity-style';
import { SnackBarCustom } from '../produit/product-validation';

export interface IAffectationZoneUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AffectationZoneUpdate = (props: IAffectationZoneUpdateProps) => {

  const [zoneId, setZoneId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [listzones, setlistzones] = useState<IZone[]>([]);
  const { affectationZoneEntity, zones, loading, updating } = props;
  const [valeurs, setvaleurs] = useState<IAffectationZone>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');


  const requiredValues = [
    { field: 'zoneId', fieldName: 'Zone ID ' },
    { field: 'zoneNom', fieldName: 'Nom Zone' },
  ];


 
  const classes = useStyles();

  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  const handleClose = () => {
    props.history.push('/affectation-zone' + props.location.search);
  };

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
      setvaleurs(props.affectationZoneEntity);
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
                  Fiche d affectation zone {valeurs.zoneNom ? valeurs.zoneNom : ''}
                </Typography>
              ) : (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche affectation zone
                </Typography>
              )}
            </Col>
            <Col md="3">
              <ButtonGroup>
                <Link to="/affectation-zone">
                  <Button id="cancel-save" variant="contained" color="primary" className={classes.button} startIcon={<ArrowBackIcon />}>
                    Retour
                  </Button>
                </Link>
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
                </Button>{' '}
              </ButtonGroup>
            </Col>
          </Row>
          <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
            <CardHeader title="Informations des zones" subheader="affectation zone" />

            <form className={classes.root}>
              <Row>
                <Col md="8">
                  <Row>
                    <TextField
                      style={{ width: '243px' }}
                      id="affectation-zone-gouvernorat"
                      label="Gouvernorat"
                      name="gouvernorat"
                      disabled
                      value={valeurs.gouvernorat ? valeurs.gouvernorat : ''}
                    />
                  </Row>
                  <br />
                  <Row>
                    <TextField
                      style={{ width: '243px' }}
                      id="affectation-zone-ville"
                      label="Ville"
                      name="ville"
                      value={valeurs.ville ? valeurs.ville : ''}
                      disabled
                    />
                  </Row>
                  <br />
                  <Row>
                    <TextField
                      style={{ width: '243px' }}
                      id="affectation-zone-localite"
                      label="Localité"
                      name="localite"
                      value={valeurs.localite ? valeurs.localite : ''}
                      disabled
                    />
                  </Row>
                  <br />
                  <Row>
                    <TextField
                      style={{ width: '243px' }}
                      id="affectation-zone-codePostal"
                      label="Code Postal"
                      name="codePostal"
                      value={valeurs.codePostal ? valeurs.codePostal : ''}
                      disabled
                    />
                  </Row>
                  <br />
                  <Row>
                    <FormControl className={classes.formControl}>
                    {listzones  && listzones.length > 0 ? (
                      <Autocomplete
                        id="livraison-zone"
                        options={listzones}
                        value={valeurs.zoneId ? getDefaultvalue(listzones, valeurs.zoneId) : ''}
                        getOptionLabel={option => option.nom}
                        onChange={(event, value) => handelAutocomplete(event, zones, value, valeurs, setvaleurs, 'zoneId', 'zoneNom')}
                        renderInput={params => <TextField {...params} name="zones" label="Zones" />}
                        style={{ width: 250 }}
                        /> ) : null }
                    </FormControl>
                  </Row>
                </Col>
              </Row>
            </form>
          </Card>{' '}
        </div>
      )}
       <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  zones: storeState.zone.entities,
  affectationZoneEntity: storeState.affectationZone.entity,
  loading: storeState.affectationZone.loading,
  updating: storeState.affectationZone.updating,
  updateSuccess: storeState.affectationZone.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(AffectationZoneUpdate);
