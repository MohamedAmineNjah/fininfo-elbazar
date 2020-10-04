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
import { IZone } from 'app/shared/model/zone.model';
import { getEntities as getZones } from 'app/entities/zone/zone.reducer';
import { IAffectationZone } from 'app/shared/model/affectation-zone.model';
import { getEntities as getAffectationZones } from 'app/entities/affectation-zone/affectation-zone.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adresse.reducer';
import { IAdresse } from 'app/shared/model/adresse.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdresseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdresseUpdate = (props: IAdresseUpdateProps) => {
  const [clientId, setClientId] = useState('0');
  const [zoneId, setZoneId] = useState('0');
  const [codePostalId, setCodePostalId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { adresseEntity, clients, zones, affectationZones, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adresse' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClients();
    props.getZones();
    props.getAffectationZones();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...adresseEntity,
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
      <div>
        <label className="form__label">Fiche adresse</label>
      </div>
      <br />
      <Row>
        <Col md="12">
          <AvForm>
            <AvGroup check>
              <Label id="principaleLabel">
                Principale
                <AvInput id="adresse-principale" type="checkbox" className="form-check-input" name="principale" />
              </Label>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <br />
      {loading ? (
        <p>Loading...</p>
      ) : (
        <AvForm model={isNew ? {} : adresseEntity} onSubmit={saveEntity}>
          {!isNew ? (
            <AvGroup>
              <Label for="adresse-id">
                <Translate contentKey="global.field.id">ID</Translate>
              </Label>
              <AvInput id="adresse-id" type="text" className="form-control" name="id" required readOnly />
            </AvGroup>
          ) : null}
          <Row>
            <Col md="4">
              <AvGroup className="productborder">
                <i className="bordertitle">Coordonnées client</i>
                <Row>
                  <AvGroup>
                    <Label id="prenomLabel" for="adresse-prenom">
                      <Translate contentKey="elbazarApp.adresse.prenom">Prenom</Translate>
                    </Label>
                    <AvField
                      id="adresse-prenom"
                      type="text"
                      name="prenom"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') },
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label id="nomLabel" for="adresse-nom">
                      <Translate contentKey="elbazarApp.adresse.nom">Nom</Translate>
                    </Label>
                    <AvField
                      id="adresse-nom"
                      type="text"
                      name="nom"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') },
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="telephoneLabel" for="adresse-telephone">
                      <Translate contentKey="elbazarApp.adresse.telephone">Telephone</Translate>
                    </Label>
                    <AvField id="adresse-telephone" type="string" className="form-control" name="telephone" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="mobileLabel" for="adresse-mobile">
                      <Translate contentKey="elbazarApp.adresse.mobile">Mobile</Translate>
                    </Label>
                    <AvField
                      id="adresse-mobile"
                      type="string"
                      className="form-control"
                      name="mobile"
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') },
                        number: { value: true, errorMessage: translate('entity.validation.number') },
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label for="adresse-client">
                      <Translate contentKey="elbazarApp.adresse.client">Client</Translate>
                    </Label>
                    <AvInput id="adresse-client" type="select" className="form-control" name="clientId">
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
                </Row>
              </AvGroup>
            </Col>
            <br />
            <Col md="8">
              <AvGroup className="productborder">
                <i className="bordertitle">Informations adresse</i>
                <Row>
                  <Col md="6">
                    <AvGroup>
                      <Label id="adresseLabel" for="adresse-adresse">
                        <Translate contentKey="elbazarApp.adresse.adresse">Adresse</Translate>
                      </Label>
                      <AvField
                        id="adresse-adresse"
                        type="text"
                        name="adresse"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') },
                        }}
                      />
                    </AvGroup>
                    <AvGroup>
                      <Label id="gouvernoratLabel" for="adresse-gouvernorat">
                        <Translate contentKey="elbazarApp.adresse.gouvernorat">Gouvernorat</Translate>
                      </Label>
                      <AvField
                        id="adresse-gouvernorat"
                        type="text"
                        name="gouvernorat"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') },
                        }}
                      />
                    </AvGroup>
                    <AvGroup>
                      <Label id="villeLabel" for="adresse-ville">
                        <Translate contentKey="elbazarApp.adresse.ville">Ville</Translate>
                      </Label>
                      <AvField
                        id="adresse-ville"
                        type="text"
                        name="ville"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') },
                        }}
                      />
                    </AvGroup>
                    <AvGroup>
                      <Label id="localiteLabel" for="adresse-localite">
                        <Translate contentKey="elbazarApp.adresse.localite">Localite</Translate>
                      </Label>
                      <AvField
                        id="adresse-localite"
                        type="text"
                        name="localite"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') },
                        }}
                      />
                    </AvGroup>
                  </Col>

                  <Col md="6">
                    <AvGroup>
                      <Label id="indicationLabel" for="adresse-indication">
                        <Translate contentKey="elbazarApp.adresse.indication">Indication</Translate>
                      </Label>
                      <AvField id="adresse-indication" type="text" name="indication" />
                    </AvGroup>
                    <AvGroup>
                      <Label for="adresse-zone">
                        <Translate contentKey="elbazarApp.adresse.zone">Zone</Translate>
                      </Label>
                      <AvInput id="adresse-zone" type="select" className="form-control" name="zoneId">
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
                    <AvGroup>
                      <Label for="adresse-codePostal">
                        <Translate contentKey="elbazarApp.adresse.codePostal">Code Postal</Translate>
                      </Label>
                      <AvInput id="adresse-codePostal" type="select" className="form-control" name="codePostalId">
                        <option value="" key="0" />
                        {affectationZones
                          ? affectationZones.map(otherEntity => (
                              <option value={otherEntity.id} key={otherEntity.id}>
                                {otherEntity.codePostal}
                              </option>
                            ))
                          : null}
                      </AvInput>
                    </AvGroup>
                  </Col>
                </Row>
              </AvGroup>
            </Col>
          </Row>
          <Button tag={Link} id="cancel-save" to="/adresse" replace color="info">
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
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  clients: storeState.client.entities,
  zones: storeState.zone.entities,
  affectationZones: storeState.affectationZone.entities,
  adresseEntity: storeState.adresse.entity,
  loading: storeState.adresse.loading,
  updating: storeState.adresse.updating,
  updateSuccess: storeState.adresse.updateSuccess,
});

const mapDispatchToProps = {
  getClients,
  getZones,
  getAffectationZones,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdresseUpdate);
