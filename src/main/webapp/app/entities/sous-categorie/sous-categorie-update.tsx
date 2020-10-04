import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import Button from '@material-ui/core/Button';
import { setFileData, openFile } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './sous-categorie.reducer';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import { Typography, TextField, FormControl, FormControlLabel, FormGroup, ButtonGroup } from '@material-ui/core';
import Switch from '@material-ui/core/Switch';
import { useStyles } from 'app/shared/util/entity-style';
import {
  handleChangeChecked,
  handleInputChange,
  numberRegex,
  getDefaultvalue,
  handelAutocomplete,
  onBlobChange,
  validateRequired,
  validateErrorOnField,
  RefRegex,
} from 'app/shared/util/entity-ui-services';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';

export interface ISousCategorieUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SousCategorieUpdate = (props: ISousCategorieUpdateProps) => {
  const [categorieId, setCategorieId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<ISousCategorie>({});
  const { sousCategorieEntity, categories, loading, updating } = props;
  const [errorText, setErrorText] = useState('');
  const [listcategories, setlistcategories] = useState<ICategorie[]>([]);
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [regexValues, setregexValues] = useState({
    position: { isOnError: false },
  });

  const handleClose = () => {
    props.history.push('/sous-categorie');
  };

  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  const requiredValues = [
    { field: 'nom', fieldName: 'Nom ' },
    { field: 'description', fieldName: 'description' },
    { field: 'position', fieldName: 'position' },
    { field: 'categorieId', fieldName: 'categorieId' },
    { field: 'categorieNom', fieldName: 'categorieNom' },
  ];

  const classes = useStyles();

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
  }, []);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.sousCategorieEntity);
      }
    }
  }, [props.loading]);

  useEffect(() => {
    if (props.categories) {
      setlistcategories([...props.categories]);
    }
  }, [props.categories]);

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
                  Fiche sous catégorie {valeurs.nom ? valeurs.nom : ''}
                </Typography>
              ) : (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche nouvelle sous catégorie
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
                  className={classes.button}
                  color="primary"
                  onClick={saveEntity}
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
                        checked={valeurs.etat}
                        onChange={e => handleChangeChecked(e, valeurs, setvaleurs)}
                        name="etat"
                        color="primary"
                      />
                    }
                    label="Activer catégorie"
                  />
                </FormGroup>
              }
              title="Détails sous catégories"
            />

            <form className={classes.root}>
              <Row>
                <Col md="8">
                  <Row>
                  <TextField
                        style={{ width: '243px' }}
                        id="sous-categorie-nom"
                        label="Nom de la sous catégorie"
                        name="nom"
                        required
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        value={valeurs.nom ? valeurs.nom : ''}
                      />
                  </Row>
                  <br />
                  <Row>
                    <TextField
                      style={{ width: '243px' }}
                      id="sous-categorie-description"
                      label="Description de la catégorie"
                      name="description"
                      value={valeurs.description ? valeurs.description : ''}
                      onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                      required
                    />
                  </Row>
                  <br />
                  <Row>
                    <TextValidator
                      id="sous-categorie-position"
                      label="Position de la sous catégorie"
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
                  <Row>
                    <FormControl className={classes.formControl}>
                      {listcategories && listcategories.length > 0 ? (
                        <Autocomplete
                          id="Gouvernorat"
                          options={listcategories}
                          value={valeurs.categorieId ? getDefaultvalue(listcategories, valeurs.categorieId) : ''}
                          getOptionLabel={option => option.nom}
                          onChange={(event, value) =>
                            handelAutocomplete(event, categories, value, valeurs, setvaleurs, 'categorieId', 'categorieNom')
                          }
                          renderInput={params => <TextField {...params} name="categories" label="Categories" required />}
                          style={{ width: 250 }}
                        />
                      ) : null}
                    </FormControl>
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
  categories: storeState.categorie.entities,
  sousCategorieEntity: storeState.sousCategorie.entity,
  loading: storeState.sousCategorie.loading,
  updating: storeState.sousCategorie.updating,
  updateSuccess: storeState.sousCategorie.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SousCategorieUpdate);
