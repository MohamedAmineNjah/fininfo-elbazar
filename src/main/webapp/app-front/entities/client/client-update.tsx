import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from '../../shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './client.reducer';
import { IClient } from '../../shared/model/client.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from '../../shared/util/date-utils';
import { mapIdList } from '../../shared/util/entity-utils';

export interface IClientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientUpdate = (props: IClientUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clientEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/client' + props.location.search);
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

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...clientEntity,
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
          <h2 id="elbazarApp.client.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="client-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="client-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="civiliteLabel" for="client-civilite">
                  <Translate contentKey="elbazarApp.client.civilite">Civilite</Translate>
                </Label>
                <AvInput
                  id="client-civilite"
                  type="select"
                  className="form-control"
                  name="civilite"
                  value={(!isNew && clientEntity.civilite) || 'M'}
                >
                  <option value="M">{translate('elbazarApp.Civilite.M')}</option>
                  <option value="Mme">{translate('elbazarApp.Civilite.Mme')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="client-prenom">
                  <Translate contentKey="elbazarApp.client.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="client-prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="client-nom">
                  <Translate contentKey="elbazarApp.client.nom">Nom</Translate>
                </Label>
                <AvField
                  id="client-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateDeNaissanceLabel" for="client-dateDeNaissance">
                  <Translate contentKey="elbazarApp.client.dateDeNaissance">Date De Naissance</Translate>
                </Label>
                <AvField
                  id="client-dateDeNaissance"
                  type="date"
                  className="form-control"
                  name="dateDeNaissance"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="client-email">
                  <Translate contentKey="elbazarApp.client.email">Email</Translate>
                </Label>
                <AvField
                  id="client-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    pattern: {
                      value: '^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$',
                      errorMessage: translate('entity.validation.pattern', {
                        pattern: '^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$',
                      }),
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mobileLabel" for="client-mobile">
                  <Translate contentKey="elbazarApp.client.mobile">Mobile</Translate>
                </Label>
                <AvField
                  id="client-mobile"
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
                <Label id="reglementLabel" for="client-reglement">
                  <Translate contentKey="elbazarApp.client.reglement">Reglement</Translate>
                </Label>
                <AvInput
                  id="client-reglement"
                  type="select"
                  className="form-control"
                  name="reglement"
                  value={(!isNew && clientEntity.reglement) || 'CarteBancaire'}
                >
                  <option value="CarteBancaire">{translate('elbazarApp.RegMod.CarteBancaire')}</option>
                  <option value="Cash">{translate('elbazarApp.RegMod.Cash')}</option>
                  <option value="Cheque">{translate('elbazarApp.RegMod.Cheque')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="etatLabel">
                  <AvInput id="client-etat" type="checkbox" className="form-check-input" name="etat" />
                  <Translate contentKey="elbazarApp.client.etat">Etat</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="inscriptionLabel" for="client-inscription">
                  <Translate contentKey="elbazarApp.client.inscription">Inscription</Translate>
                </Label>
                <AvField id="client-inscription" type="date" className="form-control" name="inscription" />
              </AvGroup>
              <AvGroup>
                <Label id="derniereVisiteLabel" for="client-derniereVisite">
                  <Translate contentKey="elbazarApp.client.derniereVisite">Derniere Visite</Translate>
                </Label>
                <AvField id="client-derniereVisite" type="date" className="form-control" name="derniereVisite" />
              </AvGroup>
              <AvGroup>
                <Label id="totalAchatLabel" for="client-totalAchat">
                  <Translate contentKey="elbazarApp.client.totalAchat">Total Achat</Translate>
                </Label>
                <AvField id="client-totalAchat" type="string" className="form-control" name="totalAchat" />
              </AvGroup>
              <AvGroup>
                <Label id="profileLabel" for="client-profile">
                  <Translate contentKey="elbazarApp.client.profile">Profile</Translate>
                </Label>
                <AvInput
                  id="client-profile"
                  type="select"
                  className="form-control"
                  name="profile"
                  value={(!isNew && clientEntity.profile) || 'Silver'}
                >
                  <option value="Silver">{translate('elbazarApp.ProfileClient.Silver')}</option>
                  <option value="Gold">{translate('elbazarApp.ProfileClient.Gold')}</option>
                  <option value="Platinium">{translate('elbazarApp.ProfileClient.Platinium')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="pointsFideliteLabel" for="client-pointsFidelite">
                  <Translate contentKey="elbazarApp.client.pointsFidelite">Points Fidelite</Translate>
                </Label>
                <AvField id="client-pointsFidelite" type="string" className="form-control" name="pointsFidelite" />
              </AvGroup>
              <AvGroup>
                <Label id="creeLeLabel" for="client-creeLe">
                  <Translate contentKey="elbazarApp.client.creeLe">Cree Le</Translate>
                </Label>
                <AvField id="client-creeLe" type="date" className="form-control" name="creeLe" />
              </AvGroup>
              <AvGroup>
                <Label id="creeParLabel" for="client-creePar">
                  <Translate contentKey="elbazarApp.client.creePar">Cree Par</Translate>
                </Label>
                <AvField id="client-creePar" type="text" name="creePar" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieLeLabel" for="client-modifieLe">
                  <Translate contentKey="elbazarApp.client.modifieLe">Modifie Le</Translate>
                </Label>
                <AvField id="client-modifieLe" type="date" className="form-control" name="modifieLe" />
              </AvGroup>
              <AvGroup>
                <Label id="modifieParLabel" for="client-modifiePar">
                  <Translate contentKey="elbazarApp.client.modifiePar">Modifie Par</Translate>
                </Label>
                <AvField id="client-modifiePar" type="text" name="modifiePar" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/client" replace color="info">
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
  clientEntity: storeState.client.entity,
  loading: storeState.client.loading,
  updating: storeState.client.updating,
  updateSuccess: storeState.client.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientUpdate);
