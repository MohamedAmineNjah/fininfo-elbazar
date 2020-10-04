import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICommande } from 'app/shared/model/commande.model';
import { getEntities as getCommandes } from 'app/entities/commande/commande.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { getEntities as getProduits } from 'app/entities/produit/produit.reducer';
import { getEntity, updateEntity, createEntity, reset } from './commande-lignes.reducer';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommandeLignesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeLignesUpdate = (props: ICommandeLignesUpdateProps) => {
  const [refCommandeId, setRefCommandeId] = useState('0');
  const [refProduitId, setRefProduitId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { commandeLignesEntity, commandes, produits, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/commande-lignes' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCommandes();
    props.getProduits();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...commandeLignesEntity,
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
          <h2 id="elbazarApp.commandeLignes.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.commandeLignes.home.createOrEditLabel">Create or edit a CommandeLignes</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : commandeLignesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="commande-lignes-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="commande-lignes-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quantiteLabel" for="commande-lignes-quantite">
                  <Translate contentKey="elbazarApp.commandeLignes.quantite">Quantite</Translate>
                </Label>
                <AvField id="commande-lignes-quantite" type="string" className="form-control" name="quantite" />
              </AvGroup>
              <AvGroup>
                <Label id="prixHTLabel" for="commande-lignes-prixHT">
                  <Translate contentKey="elbazarApp.commandeLignes.prixHT">Prix HT</Translate>
                </Label>
                <AvField id="commande-lignes-prixHT" type="string" className="form-control" name="prixHT" />
              </AvGroup>
              <AvGroup>
                <Label id="remiseLabel" for="commande-lignes-remise">
                  <Translate contentKey="elbazarApp.commandeLignes.remise">Remise</Translate>
                </Label>
                <AvField id="commande-lignes-remise" type="string" className="form-control" name="remise" />
              </AvGroup>
              <AvGroup>
                <Label id="tvaLabel" for="commande-lignes-tva">
                  <Translate contentKey="elbazarApp.commandeLignes.tva">Tva</Translate>
                </Label>
                <AvField id="commande-lignes-tva" type="string" className="form-control" name="tva" />
              </AvGroup>
              <AvGroup>
                <Label id="prixTTCLabel" for="commande-lignes-prixTTC">
                  <Translate contentKey="elbazarApp.commandeLignes.prixTTC">Prix TTC</Translate>
                </Label>
                <AvField id="commande-lignes-prixTTC" type="string" className="form-control" name="prixTTC" />
              </AvGroup>
              <AvGroup>
                <Label id="creeLeLabel" for="commande-lignes-creeLe">
                  <Translate contentKey="elbazarApp.commandeLignes.creeLe">Cree Le</Translate>
                </Label>
                <AvField id="commande-lignes-creeLe" type="date" className="form-control" name="creeLe" />
              </AvGroup>
              <AvGroup>
                <Label id="creeParLabel" for="commande-lignes-creePar">
                  <Translate contentKey="elbazarApp.commandeLignes.creePar">Cree Par</Translate>
                </Label>
                <AvField id="commande-lignes-creePar" type="text" name="creePar" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieLeLabel" for="commande-lignes-modifieLe">
                  <Translate contentKey="elbazarApp.commandeLignes.modifieLe">Modifie Le</Translate>
                </Label>
                <AvField id="commande-lignes-modifieLe" type="date" className="form-control" name="modifieLe" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieParLabel" for="commande-lignes-modifiePar">
                  <Translate contentKey="elbazarApp.commandeLignes.modifiePar">Modifie Par</Translate>
                </Label>
                <AvField id="commande-lignes-modifiePar" type="text" name="modifiePar" />
              </AvGroup>
              <AvGroup>
                <Label for="commande-lignes-refCommande">
                  <Translate contentKey="elbazarApp.commandeLignes.refCommande">Ref Commande</Translate>
                </Label>
                <AvInput id="commande-lignes-refCommande" type="select" className="form-control" name="refCommandeId">
                  <option value="" key="0" />
                  {commandes
                    ? commandes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.reference}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-lignes-refProduit">
                  <Translate contentKey="elbazarApp.commandeLignes.refProduit">Ref Produit</Translate>
                </Label>
                <AvInput id="commande-lignes-refProduit" type="select" className="form-control" name="refProduitId">
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
              <Button tag={Link} id="cancel-save" to="/commande-lignes" replace color="info">
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
  commandes: storeState.commande.entities,
  produits: storeState.produit.entities,
  commandeLignesEntity: storeState.commandeLignes.entity,
  loading: storeState.commandeLignes.loading,
  updating: storeState.commandeLignes.updating,
  updateSuccess: storeState.commandeLignes.updateSuccess,
});

const mapDispatchToProps = {
  getCommandes,
  getProduits,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeLignesUpdate);
