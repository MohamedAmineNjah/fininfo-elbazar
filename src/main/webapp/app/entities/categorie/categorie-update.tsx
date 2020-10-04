import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, Link } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { setFileData, openFile } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import { Typography, TextField, FormControl, FormControlLabel, FormGroup, ButtonGroup } from '@material-ui/core';
import Switch from '@material-ui/core/Switch';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './categorie.reducer';
import { useStyles } from 'app/shared/util/entity-style';
import {
  handleInputChange,
  handleChangeChecked,
  numberRegex,
  onBlobChange,
  validateRequired,
  validateErrorOnField,
  RefRegex,
} from 'app/shared/util/entity-ui-services';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';

export interface ICategorieUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CategorieUpdate = (props: ICategorieUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const { categorieEntity, loading, updating } = props;
  const [valeurs, setvaleurs] = useState<ICategorie>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [regexValues, setregexValues] = useState({
    position: { isOnError: false },
  });

  const requiredValues = [
    { field: 'nom', fieldName: 'Nom ' },
    { field: 'description', fieldName: 'Description' },
    { field: 'position', fieldName: 'Position' },
  ];

  const classes = useStyles();

  const handleClose = () => {
    props.history.push('/categorie');
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
      setvaleurs({});
      handleClose();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.categorieEntity);
      }
    }
  }, [props.loading]);

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
                  Fiche de la catégorie {valeurs ? valeurs.nom : ''}
                </Typography>
              ) : (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche de la nouvelle catégorie
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
          <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
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
                    label="Activer catégorie"
                  />
                </FormGroup>
              }
              title="Détails catégories"
            />
            <form className={classes.root}>
              <Row>
                <Col md="8">
                  <Row>
                  <TextField
                        id="nomLabel"
                        label="Nom de la catégorie"
                        name="nom"
                        value={valeurs ? valeurs.nom : ''}
                        required
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        style={{ width: 250 }}
                      />
                  </Row>
                  <br />
                  <Row>
                    <TextField
                      id="descriptionLabel"
                      label="Description de la catégorie"
                      name="description"
                      value={valeurs ? valeurs.description : ''}
                      onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                      required
                      multiline
                      style={{ width: 250 }}
                    />
                  </Row>
                  <br />
                  <Row>
                    <TextValidator
                      id="positionLabel"
                      label="Position de la catégorie"
                      name="position"
                      values={{
                        data: valeurs,
                        field: 'position',
                        setter: setvaleurs,
                        errorsetter: seterrorOnField,
                        regValue: regexValues,
                        globalErrorSetter: setregexValues,
                      }}
                      required
                      regx={numberRegex}
                      style={{ marginBottom: '20px', width: 250 }}
                    />
                  </Row>
                </Col>
                <Col md="4">
                </Col>
              </Row>
            </form>
          </Card>{' '}
        </div>
      )}
      <i style={{ color: 'grey' }}>* Champs obligatoires</i>
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  categorieEntity: storeState.categorie.entity,
  loading: storeState.categorie.loading,
  updating: storeState.categorie.updating,
  updateSuccess: storeState.categorie.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CategorieUpdate);
