import React, { useState, useEffect, useRef } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, Link } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import Switch from '@material-ui/core/Switch';
import SaveIcon from '@material-ui/icons/Save';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';

import { Row, Col, Label } from 'reactstrap';
import { setFileData, openFile, translate } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import { getFiltredEntities as getFiltredCategories } from 'app/entities/categorie/categorie.reducer';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { getEntities as getSousCategories } from 'app/entities/sous-categorie/sous-categorie.reducer';
import { getEntities as getFiltredSousCategories } from 'app/entities/sous-categorie/sous-categorie.reducer';
import { getEntities as getProduitUnites } from 'app/entities/produit-unite/produit-unite.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './produit.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { makeStyles } from '@material-ui/core/styles';
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
} from '@material-ui/core';
import { red } from '@material-ui/core/colors';
import './produit.scss';
import './stylebox.scss';
import { useStyles } from 'app/shared/util/entity-style';
import {
  onBlobChange,
  validateRequired,
  handleInputChange,
  handleChangeChecked,
  getDefaultvalue,
  handelAutocomplete,
  handelEnableAutocomplete,
  numberRegex,
  RefRegex,
  CodeFloat,
  validateErrorOnField,
  handleEligibleRemiseInputChange,
} from 'app/shared/util/entity-ui-services';
import Autocomplete from '@material-ui/lab/Autocomplete';
import sousCategorie from '../sous-categorie/sous-categorie';
import { TextValidator, SnackBarCustom } from './product-validation';
import { defaultValue } from 'app/shared/model/user.model';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { ICategorie } from 'app/shared/model/categorie.model';

export interface IProduitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const ProduitUpdate = (props: IProduitUpdateProps) => {
  const { produitEntity, categories, sousCategories, produitUnites, loading, updating } = props;

  const [categorieId, setCategorieId] = useState('0');
  const [sousCategorieId, setSousCategorieId] = useState('0');
  const [uniteId, setUniteId] = useState('0');
  const [valeurs, setvaleurs] = useState<IProduit>({});
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [isdisabled, setisdisabled] = useState(true);
  const [listsoucategories, setlistsoucategories] = useState<ISousCategorie[]>([]);
  const [listproduitunite, setlistproduitunite] = useState<IProduitUnite[]>([]);
  const [listcategories, setlistcategories] = useState<ICategorie[]>([]);
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [prixht, setprixht] = useState(0);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [isImageUpdated, setisImageUpdated] = useState(false);
  const [regexValues, setregexValues] = useState({
    reference: { isOnError: false },
    prixTTC: { isOnError: false },
    tauxTVA: { isOnError: false },
    stockMinimum: { isOnError: false },
    quantiteVenteMax: { isOnError: false },
    remise: { isOnError: false },
  });

  const handleCloseSnack = () => {
    setopenSnack(false);
  };
  const classes = useStyles();

  const handleClose = () => {
    props.history.push('/produit' + props.location.search);
  };

  const requiredValues = [
    { field: 'nom', fieldName: 'Nom ' },
    { field: 'reference', fieldName: 'Reference' },
    { field: 'description', fieldName: 'Description' },
    { field: 'marque', fieldName: 'Marque' },
    { field: 'sourceProduit', fieldName: 'Source produit' },
    { field: 'prixTTC', fieldName: 'Prix TTC' },
    { field: 'tauxTVA', fieldName: 'Taux TVA' },
    { field: 'quantiteVenteMax', fieldName: 'Quantite vente max ' },
    { field: 'stockMinimum', fieldName: 'Stock minimum ' },
    { field: 'categorieId', fieldName: 'CategorieId ' },
    { field: 'sousCategorieId', fieldName: 'SousCategorieId ' },
    { field: 'uniteId', fieldName: 'uniteCode' },
  ];

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
    props.getFiltredCategories();
    props.getFiltredSousCategories();
    props.getProduitUnites();
  }, []);

  useEffect(() => {
    if (props.categories) {
      const uniqueCategories = Array.from(new Set([...props.categories].map(a => a.id))).map(id => {
        return [...props.categories].find(a => a.id === id);
      });
      setlistcategories(uniqueCategories);
    }
  }, [props.categories]);

  useEffect(() => {
    if (props.sousCategories) {
      setlistsoucategories([...props.sousCategories]);
    }
  }, [props.sousCategories]);

  useEffect(() => {
    if (props.produitUnites) {
      setlistproduitunite([...props.produitUnites]);
    }
  }, [props.produitUnites]);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.produitEntity);
        setprixht(props.produitEntity.prixHT);
      }
    }
  }, [props.loading]);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = () => {
    console.log(validateRequired(valeurs, requiredValues, setopenSnackMessage));
    console.log(validateErrorOnField(regexValues));
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) && !validateErrorOnField(regexValues)) {
      if (isNew) {
        valeurs.id = null;
        !valeurs.etat ? (valeurs.etat = false) : valeurs.etat;
        !valeurs.eligibleRemise ? (valeurs.eligibleRemise = false) : valeurs.eligibleRemise;
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

  const onProductBlobChange = (isAnImage, name) => event => {
    setFileData(
      event,
      (contentType, data) => {
        setvaleurs({ ...valeurs, ['imageContentType']: contentType, [name]: data });
      },
      isAnImage
    );
    setisImageUpdated(true);
  };

  const showProductImage = () => {
    console.log(isImageUpdated);
    console.log(valeurs.imageUrl);
    console.log(valeurs.image);
    if (!isImageUpdated) {
      if (valeurs && valeurs.imageUrl) {
        return (
        <React.Fragment>
          <img src={`media/${valeurs.imageUrl}`} style={{ maxHeight: '100px' }} />
        </React.Fragment>
        );
      }
    } else {
      if (valeurs && valeurs.image) {
        return (
        <React.Fragment>
          <a onClick={openFile(valeurs.imageContentType, valeurs.image)}>
            <img src={`data:${valeurs.imageContentType};base64,${valeurs.image}`} style={{ maxHeight: '100px' }} />
          </a>
        </React.Fragment>
        );
      }
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
                    Fiche du produit {valeurs ? valeurs.reference : ''}
                  </Typography>
                ) : (
                    <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                      Fiche nouveau produit
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
                          label="Activer"
                        />
                      </FormGroup>
                    }
                    title="Paramètres généraux"
                    subheader="Paramètres généraux du produit"
                  />

                  <Row>
                    <Col md="6">
                      <form className={classes.root}>
                        <TextValidator
                          id="produit-reference"
                          label="Référence produit"
                          name="reference"
                          values={{
                            data: valeurs,
                            field: 'reference',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          regx={RefRegex}
                          required
                        />
                        <TextField
                          id="produit-nom"
                          label="Nom"
                          name="nom"
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          value={valeurs ? valeurs.nom : ''}
                          required
                        />

                        <TextField
                          id="produit-description"
                          label="Description"
                          name="description"
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          value={valeurs ? valeurs.description : ''}
                          required
                          multiline
                          rowsMax={10}
                        />

                        <TextField
                          id="produit-marque"
                          label="Marque"
                          name="marque"
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          value={valeurs ? valeurs.marque : ''}
                          required
                        />
                        <TextField
                          id="produit-nature"
                          label="Nature"
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          name="nature"
                          defaultValue={valeurs ? valeurs.nature : ''}
                          style={{ marginBottom: '20px' }}
                        />
                      </form>
                    </Col>
                    <Col md="6">
                      <form className={classes.root}>
                        <FormControl className={classes.formControl}>
                          {listproduitunite && listproduitunite.length > 0 ? (
                            <Autocomplete
                              id="uniteCode"
                              options={listproduitunite}
                              value={valeurs.uniteId ? getDefaultvalue(listproduitunite, valeurs.uniteId) : ''}
                              getOptionLabel={option => option.nom}
                              onChange={(event, value) =>
                                handelAutocomplete(event, produitUnites, value, valeurs, setvaleurs, 'uniteId', 'uniteCode')
                              }
                              renderInput={params => <TextField {...params} name="uniteCode" label="Unité Produit *" />}
                              style={{ width: 170 }}
                            />
                          ) : null}
                        </FormControl>
                        <FormControl className={classes.formControl}>
                          <InputLabel shrink htmlFor="produit-sourceProduit">
                            Source *
                        </InputLabel>
                          <NativeSelect
                            id="produit-sourceProduit"
                            className={classes.selectEmpty}
                            value={valeurs ? valeurs.sourceProduit : ''}
                            onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                            name="sourceProduit"
                            required
                            style={{ width: 170 }}
                          >
                            <option value="">Sélectionnez</option>
                            <option value={'Locale'}>Locale</option>
                            <option value={'Externe'}>Externe</option>
                          </NativeSelect>
                        </FormControl>
                        <FormControl className={classes.formControl}>
                          {listcategories && listcategories.length > 0 ? (
                            <Autocomplete
                              id="categories"
                              options={listcategories}
                              value={valeurs.categorieId ? getDefaultvalue(listcategories, valeurs.categorieId) : ''}
                              onChange={(event, value) =>
                                handelEnableAutocomplete(
                                  event,
                                  categories,
                                  value,
                                  valeurs,
                                  setvaleurs,
                                  'categorieId',
                                  'categorieNom',
                                  isdisabled,
                                  setisdisabled,
                                  props.sousCategories,
                                  setlistsoucategories
                                )
                              }
                              getOptionLabel={option => option.nom}
                              inputValue={valeurs ? valeurs.categorieNom : ''}
                              onInputChange={(event, newInputValue) => {
                                setvaleurs({ ...valeurs, ['categorieNom']: newInputValue });
                              }}
                              renderInput={params => <TextField {...params} name="categories" label="Categories *" />}
                              style={{ width: 170 }}
                            />
                          ) : null}
                        </FormControl>

                        <FormControl className={classes.formControl}>
                          {listsoucategories && listsoucategories.length > 0 ? (
                            <Autocomplete
                              id="sousCategories"
                              options={listsoucategories}
                              value={valeurs.sousCategorieId ? getDefaultvalue(listsoucategories, valeurs.sousCategorieId) : ''}
                              getOptionLabel={option => option.nom}
                              onChange={(event, value) =>
                                handelAutocomplete(event, sousCategories, value, valeurs, setvaleurs, 'sousCategorieId', 'sousCategorieNom')
                              }
                              inputValue={valeurs ? valeurs.sousCategorieNom : ''}
                              onInputChange={(event, newInputValue) => {
                                setvaleurs({ ...valeurs, ['sousCategorieNom']: newInputValue });
                              }}
                              renderInput={params => <TextField {...params} name="sousCategories" label="Sous Catégories *" />}
                              style={{ width: 170 }}
                              disabled={isdisabled}
                            />
                          ) : null}
                        </FormControl>
                        <FormControl style={{ marginTop: '20px' }}>
                          <div>
                            {showProductImage()}
                            <input
                              accept="image/*"
                              className={classes.input}
                              id="file_image"
                              type="file"
                              onChange={onProductBlobChange(true, 'image')}
                            />
                            <label htmlFor="file_image">
                              <Button variant="outlined" color="primary" component="span">
                                {valeurs ? (!valeurs.imageUrl ? 'Ajouter Image *' : 'Modifier Image') : ''}
                              </Button>
                            </label>
                            <input type="hidden" name="image" value={valeurs ? valeurs.image : ''} required />
                            <br />
                          </div>
                        </FormControl>
                      </form>
                    </Col>
                  </Row>
                </Card>
              </Col>

              <Col>
                <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                  <CardHeader
                    action={
                      <FormGroup>
                        <FormControlLabel
                          control={
                            <Switch
                              checked={valeurs ? valeurs.eligibleRemise : false}
                              onChange={e => handleEligibleRemiseInputChange(e, valeurs, setvaleurs)}
                              name="eligibleRemise"
                              color="primary"
                            />
                          }
                          label="Eligible Remise"
                        />
                      </FormGroup>
                    }
                    title="Prix"
                    subheader="Informations concernant les détails du prix de produit"
                  />

                  <Row>
                    <Col md="6">
                      <form className={classes.root}>
                        <TextField
                          id="produit-prixHT"
                          label="Prix HT en TND"
                          type="double"
                          name="prixHT"
                          value={valeurs ? valeurs.prixHT : 0}
                          style={{ marginTop: '20px' }}
                          InputLabelProps={{
                            shrink: true,
                          }}
                          disabled
                        />
                        <TextValidator
                          id="produit-prixTTC"
                          label="Prix TTC en TND"
                          type="double"
                          name="prixTTC"
                          values={{
                            data: valeurs,
                            field: 'prixTTC',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          regx={CodeFloat}
                          required
                        />

                        <TextField
                          id="produit-debutPromo"
                          label="Date Début Promo"
                          type="date"
                          name="debutPromo"
                          value={valeurs ? valeurs.debutPromo : ''}
                          style={{ marginTop: '20px' }}
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          InputLabelProps={{
                            shrink: true,
                          }}
                          disabled={valeurs ? !valeurs.eligibleRemise : true}
                        />
                        <FormControl className={classes.formControl} style={{ marginTop: '20px' }}>
                          <InputLabel shrink htmlFor="produit-rating">
                            Note Produit
                        </InputLabel>
                          <NativeSelect
                            id="produit-rating"
                            className={classes.selectEmpty}
                            value={valeurs ? valeurs.rating : ''}
                            onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                            name="rating"
                          >
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                          </NativeSelect>
                        </FormControl>
                      </form>
                    </Col>

                    <Col md="6">
                      <form className={classes.root}>
                        <TextValidator
                          id="produit-tauxTVA"
                          label="Taux TVA en %"
                          type="double"
                          name="tauxTVA"
                          values={{
                            data: valeurs,
                            field: 'tauxTVA',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          regx={CodeFloat}
                          required
                        />

                        <TextValidator
                          id="produit-remise"
                          label="Remise"
                          name="remise"
                          values={{
                            data: valeurs,
                            field: 'remise',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          value={valeurs ? valeurs.remise : ''}
                          regx={numberRegex}
                          disabled={valeurs ? !valeurs.eligibleRemise : true}
                        />

                        <TextField
                          id="produit-finPromo"
                          label="Date Fin Promo"
                          type="date"
                          name="finPromo"
                          value={valeurs ? valeurs.finPromo : ''}
                          style={{ marginTop: '20px' }}
                          onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                          InputLabelProps={{
                            shrink: true,
                          }}
                          disabled={valeurs ? !valeurs.eligibleRemise : true}
                        />
                      </form>
                    </Col>
                  </Row>
                </Card>
              </Col>
              <Col>
                <Card className={(classes.root2, 'container')} style={{ height: '100%' }}>
                  <CardHeader title="Stock" subheader="Informations concernant le stock produit" />
                  <Row>
                    <Col md="12">
                      <form className={classes.root}>
                        <FormControlLabel
                          control={<Checkbox checked={valeurs ? valeurs.enVedette : false} name="enVedette" color="primary" />}
                          label="Produit en vedette"
                          onChange={e => handleChangeChecked(e, valeurs, setvaleurs)}
                        />
                      </form>
                    </Col>
                  </Row>
                  <Row>
                    <Col md="6">
                      <form className={classes.root}>
                        <TextValidator
                          id="produit-StockMinimum"
                          label="Stock Minimum"
                          name="stockMinimum"
                          values={{
                            data: valeurs,
                            field: 'stockMinimum',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          required
                          regx={numberRegex}
                          style={{ marginBottom: '20px' }}
                        />
                      </form>
                    </Col>
                    <Col md="6">
                      <form className={classes.root}>
                        <TextValidator
                          id="produit-quantiteVenteMax"
                          label="Quantite Vente Max"
                          name="quantiteVenteMax"
                          values={{
                            data: valeurs,
                            field: 'quantiteVenteMax',
                            setter: setvaleurs,
                            errorsetter: seterrorOnField,
                            regValue: regexValues,
                            globalErrorSetter: setregexValues,
                          }}
                          required
                          regx={numberRegex}
                          style={{ marginBottom: '20px' }}
                        />
                      </form>
                    </Col>
                  </Row>
                </Card>
              </Col>
            </Row>
          </div>
        )}
      <i style={{ color: 'grey' }}>* Champs obligatoires</i>
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  categories: storeState.categorie.entities,
  sousCategories: storeState.sousCategorie.entities,
  produitUnites: storeState.produitUnite.entities,
  produitEntity: storeState.produit.entity,
  loading: storeState.produit.loading,
  updating: storeState.produit.updating,
  updateSuccess: storeState.produit.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getFiltredCategories,
  getSousCategories,
  getFiltredSousCategories,
  getProduitUnites,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitUpdate);
