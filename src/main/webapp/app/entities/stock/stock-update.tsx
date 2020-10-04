import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduit } from 'app/shared/model/produit.model';
import { getEntities as getProduits } from 'app/entities/produit/produit.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { getEntities as getSousCategories } from 'app/entities/sous-categorie/sous-categorie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './stock.reducer';
import { IStock } from 'app/shared/model/stock.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStockUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockUpdate = (props: IStockUpdateProps) => {
  const [refProduitId, setRefProduitId] = useState('0');
  const [idCategorieId, setIdCategorieId] = useState('0');
  const [idSousCategorieId, setIdSousCategorieId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { stockEntity, produits, categories, sousCategories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/stock' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProduits();
    props.getCategories();
    props.getSousCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...stockEntity,
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
          <h2 id="elbazarApp.stock.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.stock.home.createOrEditLabel">Create or edit a Stock</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : stockEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="stock-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="stock-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="stockReserveLabel" for="stock-stockReserve">
                  <Translate contentKey="elbazarApp.stock.stockReserve">Stock Reserve</Translate>
                </Label>
                <AvField id="stock-stockReserve" type="string" className="form-control" name="stockReserve" />
              </AvGroup>
              <AvGroup>
                <Label id="stockCommandeLabel" for="stock-stockCommande">
                  <Translate contentKey="elbazarApp.stock.stockCommande">Stock Commande</Translate>
                </Label>
                <AvField id="stock-stockCommande" type="string" className="form-control" name="stockCommande" />
              </AvGroup>
              <AvGroup>
                <Label id="stockPhysiqueLabel" for="stock-stockPhysique">
                  <Translate contentKey="elbazarApp.stock.stockPhysique">Stock Physique</Translate>
                </Label>
                <AvField id="stock-stockPhysique" type="string" className="form-control" name="stockPhysique" />
              </AvGroup>
              <AvGroup>
                <Label id="stockDisponibleLabel" for="stock-stockDisponible">
                  <Translate contentKey="elbazarApp.stock.stockDisponible">Stock Disponible</Translate>
                </Label>
                <AvField id="stock-stockDisponible" type="string" className="form-control" name="stockDisponible" />
              </AvGroup>
              <AvGroup>
                <Label id="stockMinimumLabel" for="stock-stockMinimum">
                  <Translate contentKey="elbazarApp.stock.stockMinimum">Stock Minimum</Translate>
                </Label>
                <AvField id="stock-stockMinimum" type="string" className="form-control" name="stockMinimum" />
              </AvGroup>
              <AvGroup>
                <Label id="derniereEntreLabel" for="stock-derniereEntre">
                  <Translate contentKey="elbazarApp.stock.derniereEntre">Derniere Entre</Translate>
                </Label>
                <AvField id="stock-derniereEntre" type="date" className="form-control" name="derniereEntre" />
              </AvGroup>
              <AvGroup>
                <Label id="derniereSortieLabel" for="stock-derniereSortie">
                  <Translate contentKey="elbazarApp.stock.derniereSortie">Derniere Sortie</Translate>
                </Label>
                <AvField id="stock-derniereSortie" type="date" className="form-control" name="derniereSortie" />
              </AvGroup>
              <AvGroup check>
                <Label id="alerteStockLabel">
                  <AvInput id="stock-alerteStock" type="checkbox" className="form-check-input" name="alerteStock" />
                  <Translate contentKey="elbazarApp.stock.alerteStock">Alerte Stock</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="creeLeLabel" for="stock-creeLe">
                  <Translate contentKey="elbazarApp.stock.creeLe">Cree Le</Translate>
                </Label>
                <AvField id="stock-creeLe" type="date" className="form-control" name="creeLe" />
              </AvGroup>
              <AvGroup>
                <Label id="creeParLabel" for="stock-creePar">
                  <Translate contentKey="elbazarApp.stock.creePar">Cree Par</Translate>
                </Label>
                <AvField id="stock-creePar" type="text" name="creePar" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieLeLabel" for="stock-modifieLe">
                  <Translate contentKey="elbazarApp.stock.modifieLe">Modifie Le</Translate>
                </Label>
                <AvField id="stock-modifieLe" type="date" className="form-control" name="modifieLe" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieParLabel" for="stock-modifiePar">
                  <Translate contentKey="elbazarApp.stock.modifiePar">Modifie Par</Translate>
                </Label>
                <AvField id="stock-modifiePar" type="text" name="modifiePar" />
              </AvGroup>
              <AvGroup>
                <Label for="stock-refProduit">
                  <Translate contentKey="elbazarApp.stock.refProduit">Ref Produit</Translate>
                </Label>
                <AvInput id="stock-refProduit" type="select" className="form-control" name="refProduitId">
                  <option value="" key="0" />
                  {produits
                    ? produits.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.reference}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="stock-idCategorie">
                  <Translate contentKey="elbazarApp.stock.idCategorie">Id Categorie</Translate>
                </Label>
                <AvInput id="stock-idCategorie" type="select" className="form-control" name="idCategorieId">
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
                <Label for="stock-idSousCategorie">
                  <Translate contentKey="elbazarApp.stock.idSousCategorie">Id Sous Categorie</Translate>
                </Label>
                <AvInput id="stock-idSousCategorie" type="select" className="form-control" name="idSousCategorieId">
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
              <Button tag={Link} id="cancel-save" to="/stock" replace color="info">
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
  produits: storeState.produit.entities,
  categories: storeState.categorie.entities,
  sousCategories: storeState.sousCategorie.entities,
  stockEntity: storeState.stock.entity,
  loading: storeState.stock.loading,
  updating: storeState.stock.updating,
  updateSuccess: storeState.stock.updateSuccess,
});

const mapDispatchToProps = {
  getProduits,
  getCategories,
  getSousCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockUpdate);
