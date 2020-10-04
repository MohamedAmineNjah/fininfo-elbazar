import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IAdresse } from 'app/shared/model/adresse.model';
import { getEntities as getAdresses } from 'app/entities/adresse/adresse.reducer';
import { IZone } from 'app/shared/model/zone.model';
import { getEntities as getZones } from 'app/entities/zone/zone.reducer';
import { getEntity, updateEntity, createEntity, reset } from './commande.reducer';
import { ICommande } from 'app/shared/model/commande.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommandeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeUpdate = (props: ICommandeUpdateProps) => {
  const [idClientId, setIdClientId] = useState('0');
  const [idAdresseId, setIdAdresseId] = useState('0');
  const [zoneId, setZoneId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { commandeEntity, clients, adresses, zones, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/commande' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClients();
    props.getAdresses();
    props.getZones();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...commandeEntity,
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
          <h2 id="elbazarApp.commande.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.commande.home.createOrEditLabel">Create or edit a Commande</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : commandeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="commande-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="commande-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="referenceLabel" for="commande-reference">
                  <Translate contentKey="elbazarApp.commande.reference">Reference</Translate>
                </Label>
                <AvField
                  id="commande-reference"
                  type="text"
                  name="reference"
                  validate={{
                    pattern: {
                      value: '^[a-zA-Z0-9]{0,12}$',
                      errorMessage: translate('entity.validation.pattern', { pattern: '^[a-zA-Z0-9]{0,12}$' }),
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="commande-date">
                  <Translate contentKey="elbazarApp.commande.date">Date</Translate>
                </Label>
                <AvField id="commande-date" type="date" className="form-control" name="date" />
              </AvGroup>
              <AvGroup>
                <Label id="statutLabel" for="commande-statut">
                  <Translate contentKey="elbazarApp.commande.statut">Statut</Translate>
                </Label>
                <AvInput
                  id="commande-statut"
                  type="select"
                  className="form-control"
                  name="statut"
                  value={(!isNew && commandeEntity.statut) || 'Reservee'}
                >
                  <option value="Reservee">{translate('elbazarApp.StatCmd.Reservee')}</option>
                  <option value="Commandee">{translate('elbazarApp.StatCmd.Commandee')}</option>
                  <option value="Livraison">{translate('elbazarApp.StatCmd.Livraison')}</option>
                  <option value="Livree">{translate('elbazarApp.StatCmd.Livree')}</option>
                  <option value="Annulee">{translate('elbazarApp.StatCmd.Annulee')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="origineLabel" for="commande-origine">
                  <Translate contentKey="elbazarApp.commande.origine">Origine</Translate>
                </Label>
                <AvInput
                  id="commande-origine"
                  type="select"
                  className="form-control"
                  name="origine"
                  value={(!isNew && commandeEntity.origine) || 'Client'}
                >
                  <option value="Client">{translate('elbazarApp.Origine.Client')}</option>
                  <option value="Admin">{translate('elbazarApp.Origine.Admin')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="totalHTLabel" for="commande-totalHT">
                  <Translate contentKey="elbazarApp.commande.totalHT">Total HT</Translate>
                </Label>
                <AvField id="commande-totalHT" type="string" className="form-control" name="totalHT" />
              </AvGroup>
              <AvGroup>
                <Label id="totalTVALabel" for="commande-totalTVA">
                  <Translate contentKey="elbazarApp.commande.totalTVA">Total TVA</Translate>
                </Label>
                <AvField id="commande-totalTVA" type="string" className="form-control" name="totalTVA" />
              </AvGroup>
              <AvGroup>
                <Label id="totalRemiseLabel" for="commande-totalRemise">
                  <Translate contentKey="elbazarApp.commande.totalRemise">Total Remise</Translate>
                </Label>
                <AvField id="commande-totalRemise" type="string" className="form-control" name="totalRemise" />
              </AvGroup>
              <AvGroup>
                <Label id="totTTCLabel" for="commande-totTTC">
                  <Translate contentKey="elbazarApp.commande.totTTC">Tot TTC</Translate>
                </Label>
                <AvField id="commande-totTTC" type="string" className="form-control" name="totTTC" />
              </AvGroup>
              <AvGroup>
                <Label id="deviseLabel" for="commande-devise">
                  <Translate contentKey="elbazarApp.commande.devise">Devise</Translate>
                </Label>
                <AvInput
                  id="commande-devise"
                  type="select"
                  className="form-control"
                  name="devise"
                  value={(!isNew && commandeEntity.devise) || 'TND'}
                >
                  <option value="TND">{translate('elbazarApp.Devise.TND')}</option>
                  <option value="EUR">{translate('elbazarApp.Devise.EUR')}</option>
                  <option value="USD">{translate('elbazarApp.Devise.USD')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="pointsFideliteLabel" for="commande-pointsFidelite">
                  <Translate contentKey="elbazarApp.commande.pointsFidelite">Points Fidelite</Translate>
                </Label>
                <AvField id="commande-pointsFidelite" type="string" className="form-control" name="pointsFidelite" />
              </AvGroup>
              <AvGroup>
                <Label id="reglementLabel" for="commande-reglement">
                  <Translate contentKey="elbazarApp.commande.reglement">Reglement</Translate>
                </Label>
                <AvInput
                  id="commande-reglement"
                  type="select"
                  className="form-control"
                  name="reglement"
                  value={(!isNew && commandeEntity.reglement) || 'CarteBancaire'}
                >
                  <option value="CarteBancaire">{translate('elbazarApp.RegMod.CarteBancaire')}</option>
                  <option value="Cash">{translate('elbazarApp.RegMod.Cash')}</option>
                  <option value="Cheque">{translate('elbazarApp.RegMod.Cheque')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="dateLivraisonLabel" for="commande-dateLivraison">
                  <Translate contentKey="elbazarApp.commande.dateLivraison">Date Livraison</Translate>
                </Label>
                <AvField id="commande-dateLivraison" type="date" className="form-control" name="dateLivraison" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreationLabel" for="commande-dateCreation">
                  <Translate contentKey="elbazarApp.commande.dateCreation">Date Creation</Translate>
                </Label>
                <AvField id="commande-dateCreation" type="date" className="form-control" name="dateCreation" />
              </AvGroup>
              <AvGroup>
                <Label id="dateAnnulationLabel" for="commande-dateAnnulation">
                  <Translate contentKey="elbazarApp.commande.dateAnnulation">Date Annulation</Translate>
                </Label>
                <AvField id="commande-dateAnnulation" type="date" className="form-control" name="dateAnnulation" />
              </AvGroup>
              <AvGroup>
                <Label id="creeLeLabel" for="commande-creeLe">
                  <Translate contentKey="elbazarApp.commande.creeLe">Cree Le</Translate>
                </Label>
                <AvField id="commande-creeLe" type="date" className="form-control" name="creeLe" />
              </AvGroup>
              <AvGroup>
                <Label id="creeParLabel" for="commande-creePar">
                  <Translate contentKey="elbazarApp.commande.creePar">Cree Par</Translate>
                </Label>
                <AvField id="commande-creePar" type="text" name="creePar" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieLeLabel" for="commande-modifieLe">
                  <Translate contentKey="elbazarApp.commande.modifieLe">Modifie Le</Translate>
                </Label>
                <AvField id="commande-modifieLe" type="date" className="form-control" name="modifieLe" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieParLabel" for="commande-modifiePar">
                  <Translate contentKey="elbazarApp.commande.modifiePar">Modifie Par</Translate>
                </Label>
                <AvField id="commande-modifiePar" type="text" name="modifiePar" />
              </AvGroup>
              <AvGroup>
                <Label for="commande-idClient">
                  <Translate contentKey="elbazarApp.commande.idClient">Id Client</Translate>
                </Label>
                <AvInput id="commande-idClient" type="select" className="form-control" name="idClientId">
                  <option value="" key="0" />
                  {clients
                    ? clients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-idAdresse">
                  <Translate contentKey="elbazarApp.commande.idAdresse">Id Adresse</Translate>
                </Label>
                <AvInput id="commande-idAdresse" type="select" className="form-control" name="idAdresseId">
                  <option value="" key="0" />
                  {adresses
                    ? adresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="commande-zone">
                  <Translate contentKey="elbazarApp.commande.zone">Zone</Translate>
                </Label>
                <AvInput id="commande-zone" type="select" className="form-control" name="zoneId">
                  <option value="" key="0" />
                  {zones
                    ? zones.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/commande" replace color="info">
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
  clients: storeState.client.entities,
  adresses: storeState.adresse.entities,
  zones: storeState.zone.entities,
  commandeEntity: storeState.commande.entity,
  loading: storeState.commande.loading,
  updating: storeState.commande.updating,
  updateSuccess: storeState.commande.updateSuccess,
});

const mapDispatchToProps = {
  getClients,
  getAdresses,
  getZones,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeUpdate);
