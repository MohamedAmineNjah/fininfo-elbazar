import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './gestion-fidelite.reducer';

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
  Input,
  InputAdornment,
} from '@material-ui/core';
import { red } from '@material-ui/core/colors';
import Switch from '@material-ui/core/Switch';
import { IGestionFidelite } from 'app/shared/model/gestion-fidelite.model';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';
import { useStyles } from 'app/shared/util/entity-style';
import { validateRequired, numberRegex, CodeFloat } from 'app/shared/util/entity-ui-services';

export interface IGestionFideliteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GestionFideliteUpdate = (props: IGestionFideliteUpdateProps) => {
  const { gestionFideliteEntity, loading, updating } = props;

  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IGestionFidelite>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');

  const requiredValues = [{ field: 'valeur', fieldName: 'Valeur ' }];

  const classes = useStyles();

  const handleInputChange = (event: any) => {
    const { name, value } = event.currentTarget;
    setvaleurs({ ...valeurs, [name]: value });
  };

  const handleClose = () => {
    props.history.push('/gestion-fidelite');
  };

  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    if (!props.loading) {
      setvaleurs(props.gestionFideliteEntity);
    }
  }, [props.loading]);

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
                  Configuration de la formule de fidélité: {valeurs.nom ? valeurs.nom : ''}
                </Typography>
              ) : null}
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
          <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
            <CardHeader title="Paramétrage formule de fidélité" />

            <form className={classes.root}>
              <Row>
                <Col md="6">
                  <TextField
                    id="pointsLabel"
                    label="Points"
                    name="points"
                    defaultValue={valeurs.points ? valeurs.points : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
                <Col md="6">
                  <TextValidator
                    id="gestion-fidelite-valeur"
                    label="Valeur en TND"
                    name="valeur"
                    values={{ data: valeurs, field: 'valeur', setter: setvaleurs, errorsetter: seterrorOnField }}
                    required
                    regx={CodeFloat}
                    style={{ marginBottom: '20px', width: 200 }}
                  />
                </Col>
              </Row>
              <Row>
                <Col md="6">
                  <TextField
                    id="gestion-fidelite-silverMin"
                    label="Silver min"
                    name="silverMin"
                    onChange={handleInputChange}
                    value={valeurs.silverMin}
                    variant="outlined"
                    disabled
                  />
                </Col>
                <Col md="6">
                  <TextField
                    id="gestion-fidelite-silverMax"
                    label="Silver max"
                    name="silverMax"
                    onChange={handleInputChange}
                    value={valeurs.silverMax ? valeurs.silverMax : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
              </Row>
              <Row>
                <Col md="6">
                  <TextField
                    id="gestion-fidelite-goldMin"
                    label="Gold min"
                    name="goldMin"
                    onChange={handleInputChange}
                    value={valeurs.goldMin ? valeurs.goldMin : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
                <Col md="6">
                  {' '}
                  <TextField
                    id="gestion-fidelite-goldMax"
                    label="Gold max"
                    name="goldMax"
                    onChange={handleInputChange}
                    value={valeurs.goldMax ? valeurs.goldMax : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
              </Row>
              <Row>
                <Col md="6">
                  <TextField
                    id="gestion-fidelite-platiniumMin"
                    label="platinum min"
                    name="platiniumMin"
                    onChange={handleInputChange}
                    value={valeurs.platiniumMin ? valeurs.platiniumMin : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
                <Col md="6">
                  <TextField
                    id="gestion-fidelite-platiniumMax"
                    label="Platinum max"
                    name="platiniumMax"
                    onChange={handleInputChange}
                    value={valeurs.platiniumMax ? valeurs.platiniumMax : ''}
                    variant="outlined"
                    disabled
                  />
                </Col>
              </Row>
            </form>
          </Card>
        </div>
      )}
      <i style={{ color: 'grey' }}>* Champs obligatoires</i>
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  gestionFideliteEntity: storeState.gestionFidelite.entity,
  loading: storeState.gestionFidelite.loading,
  updating: storeState.gestionFidelite.updating,
  updateSuccess: storeState.gestionFidelite.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GestionFideliteUpdate);
