import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { getEntities as getSousCategories } from 'app/entities/sous-categorie/sous-categorie.reducer';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { getEntities as getProduitUnites } from 'app/entities/produit-unite/produit-unite.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './produit.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProduitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProduitUpdate = (props: IProduitUpdateProps) => {
  const [categorieId, setCategorieId] = useState('0');
  const [sousCategorieId, setSousCategorieId] = useState('0');
  const [uniteId, setUniteId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { produitEntity, categories, sousCategories, produitUnites, loading, updating } = props;

  const { image, imageContentType } = produitEntity;

  const handleClose = () => {
    props.history.push('/produit' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
    props.getSousCategories();
    props.getProduitUnites();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...produitEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="elbazarApp.produit.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.produit.home.createOrEditLabel">Create or edit a Produit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : produitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="produit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="produit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="referenceLabel" for="produit-reference">
                  <Translate contentKey="elbazarApp.produit.reference">Reference</Translate>
                </Label>
                <AvField
                  id="produit-reference"
                  type="text"
                  name="reference"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    pattern: {
                      value: '^([\\S]{1,25}$)*',
                      errorMessage: translate('entity.validation.pattern', { pattern: '^([\\S]{1,25}$)*' }),
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="produit-nom">
                  <Translate contentKey="elbazarApp.produit.nom">Nom</Translate>
                </Label>
                <AvField
                  id="produit-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="codeBarreLabel" for="produit-codeBarre">
                  <Translate contentKey="elbazarApp.produit.codeBarre">Code Barre</Translate>
                </Label>
                <AvField id="produit-codeBarre" type="text" name="codeBarre" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="produit-description">
                  <Translate contentKey="elbazarApp.produit.description">Description</Translate>
                </Label>
                <AvField
                  id="produit-description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="etatLabel">
                  <AvInput id="produit-etat" type="checkbox" className="form-check-input" name="etat" />
                  <Translate contentKey="elbazarApp.produit.etat">Etat</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="marqueLabel" for="produit-marque">
                  <Translate contentKey="elbazarApp.produit.marque">Marque</Translate>
                </Label>
                <AvField
                  id="produit-marque"
                  type="text"
                  name="marque"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="natureLabel" for="produit-nature">
                  <Translate contentKey="elbazarApp.produit.nature">Nature</Translate>
                </Label>
                <AvField id="produit-nature" type="text" name="nature" />
              </AvGroup>
              <AvGroup>
                <Label id="stockMinimumLabel" for="produit-stockMinimum">
                  <Translate contentKey="elbazarApp.produit.stockMinimum">Stock Minimum</Translate>
                </Label>
                <AvField
                  id="produit-stockMinimum"
                  type="string"
                  className="form-control"
                  name="stockMinimum"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="quantiteVenteMaxLabel" for="produit-quantiteVenteMax">
                  <Translate contentKey="elbazarApp.produit.quantiteVenteMax">Quantite Vente Max</Translate>
                </Label>
                <AvField
                  id="produit-quantiteVenteMax"
                  type="string"
                  className="form-control"
                  name="quantiteVenteMax"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="horsStockLabel">
                  <AvInput id="produit-horsStock" type="checkbox" className="form-check-input" name="horsStock" />
                  <Translate contentKey="elbazarApp.produit.horsStock">Hors Stock</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="typeServiceLabel">
                  <AvInput id="produit-typeService" type="checkbox" className="form-check-input" name="typeService" />
                  <Translate contentKey="elbazarApp.produit.typeService">Type Service</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="datePremptionLabel" for="produit-datePremption">
                  <Translate contentKey="elbazarApp.produit.datePremption">Date Premption</Translate>
                </Label>
                <AvField id="produit-datePremption" type="date" className="form-control" name="datePremption" />
              </AvGroup>
              <AvGroup>
                <Label id="prixHTLabel" for="produit-prixHT">
                  <Translate contentKey="elbazarApp.produit.prixHT">Prix HT</Translate>
                </Label>
                <AvField
                  id="produit-prixHT"
                  type="string"
                  className="form-control"
                  name="prixHT"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tauxTVALabel" for="produit-tauxTVA">
                  <Translate contentKey="elbazarApp.produit.tauxTVA">Taux TVA</Translate>
                </Label>
                <AvField
                  id="produit-tauxTVA"
                  type="string"
                  className="form-control"
                  name="tauxTVA"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prixTTCLabel" for="produit-prixTTC">
                  <Translate contentKey="elbazarApp.produit.prixTTC">Prix TTC</Translate>
                </Label>
                <AvField
                  id="produit-prixTTC"
                  type="string"
                  className="form-control"
                  name="prixTTC"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deviseLabel" for="produit-devise">
                  <Translate contentKey="elbazarApp.produit.devise">Devise</Translate>
                </Label>
                <AvInput
                  id="produit-devise"
                  type="select"
                  className="form-control"
                  name="devise"
                  value={(!isNew && produitEntity.devise) || 'TND'}
                >
                  <option value="TND">{translate('elbazarApp.Devise.TND')}</option>
                  <option value="EUR">{translate('elbazarApp.Devise.EUR')}</option>
                  <option value="USD">{translate('elbazarApp.Devise.USD')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="sourceProduitLabel" for="produit-sourceProduit">
                  <Translate contentKey="elbazarApp.produit.sourceProduit">Source Produit</Translate>
                </Label>
                <AvInput
                  id="produit-sourceProduit"
                  type="select"
                  className="form-control"
                  name="sourceProduit"
                  value={(!isNew && produitEntity.sourceProduit) || 'Locale'}
                >
                  <option value="Locale">{translate('elbazarApp.SourcePrd.Locale')}</option>
                  <option value="Externe">{translate('elbazarApp.SourcePrd.Externe')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="ratingLabel" for="produit-rating">
                  <Translate contentKey="elbazarApp.produit.rating">Rating</Translate>
                </Label>
                <AvField
                  id="produit-rating"
                  type="text"
                  name="rating"
                  validate={{
                    pattern: { value: '^[1-5]$', errorMessage: translate('entity.validation.pattern', { pattern: '^[1-5]$' }) },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="eligibleRemiseLabel">
                  <AvInput id="produit-eligibleRemise" type="checkbox" className="form-check-input" name="eligibleRemise" />
                  <Translate contentKey="elbazarApp.produit.eligibleRemise">Eligible Remise</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="remiseLabel" for="produit-remise">
                  <Translate contentKey="elbazarApp.produit.remise">Remise</Translate>
                </Label>
                <AvField id="produit-remise" type="string" className="form-control" name="remise" />
              </AvGroup>
              <AvGroup>
                <Label id="debutPromoLabel" for="produit-debutPromo">
                  <Translate contentKey="elbazarApp.produit.debutPromo">Debut Promo</Translate>
                </Label>
                <AvField id="produit-debutPromo" type="date" className="form-control" name="debutPromo" />
              </AvGroup>
              <AvGroup>
                <Label id="finPromoLabel" for="produit-finPromo">
                  <Translate contentKey="elbazarApp.produit.finPromo">Fin Promo</Translate>
                </Label>
                <AvField id="produit-finPromo" type="date" className="form-control" name="finPromo" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="imageLabel" for="image">
                    <Translate contentKey="elbazarApp.produit.image">Image</Translate>
                  </Label>
                  <br />
                  {image ? (
                    <div>
                      {imageContentType ? (
                        <a onClick={openFile(imageContentType, image)}>
                          <img src={`data:${imageContentType};base64,${image}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {imageContentType}, {byteSize(image)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('image')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_image" type="file" onChange={onBlobChange(true, 'image')} accept="image/*" />
                  <AvInput
                    type="hidden"
                    name="image"
                    value={image}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                    }}
                  />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="creeLeLabel" for="produit-creeLe">
                  <Translate contentKey="elbazarApp.produit.creeLe">Cree Le</Translate>
                </Label>
                <AvField id="produit-creeLe" type="date" className="form-control" name="creeLe" />
              </AvGroup>
              <AvGroup>
                <Label id="creeParLabel" for="produit-creePar">
                  <Translate contentKey="elbazarApp.produit.creePar">Cree Par</Translate>
                </Label>
                <AvField id="produit-creePar" type="text" name="creePar" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieLeLabel" for="produit-modifieLe">
                  <Translate contentKey="elbazarApp.produit.modifieLe">Modifie Le</Translate>
                </Label>
                <AvField id="produit-modifieLe" type="date" className="form-control" name="modifieLe" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieParLabel" for="produit-modifiePar">
                  <Translate contentKey="elbazarApp.produit.modifiePar">Modifie Par</Translate>
                </Label>
                <AvField id="produit-modifiePar" type="text" name="modifiePar" />
              </AvGroup>
              <AvGroup>
                <Label for="produit-categorie">
                  <Translate contentKey="elbazarApp.produit.categorie">Categorie</Translate>
                </Label>
                <AvInput id="produit-categorie" type="select" className="form-control" name="categorieId">
                  <option value="" key="0" />
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="produit-sousCategorie">
                  <Translate contentKey="elbazarApp.produit.sousCategorie">Sous Categorie</Translate>
                </Label>
                <AvInput id="produit-sousCategorie" type="select" className="form-control" name="sousCategorieId">
                  <option value="" key="0" />
                  {sousCategories
                    ? sousCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="produit-unite">
                  <Translate contentKey="elbazarApp.produit.unite">Unite</Translate>
                </Label>
                <AvInput id="produit-unite" type="select" className="form-control" name="uniteId">
                  <option value="" key="0" />
                  {produitUnites
                    ? produitUnites.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/produit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
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
  getSousCategories,
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
