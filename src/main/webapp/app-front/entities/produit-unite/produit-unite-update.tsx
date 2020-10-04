import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './produit-unite.reducer';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProduitUniteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProduitUniteUpdate = (props: IProduitUniteUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { produitUniteEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/produit-unite');
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
        ...produitUniteEntity,
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
          <h2 id="elbazarApp.produitUnite.home.createOrEditLabel">
            <Translate contentKey="elbazarApp.produitUnite.home.createOrEditLabel">Create or edit a ProduitUnite</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : produitUniteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="produit-unite-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="produit-unite-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="produit-unite-code">
                  <Translate contentKey="elbazarApp.produitUnite.code">Code</Translate>
                </Label>
                <AvField
                  id="produit-unite-code"
                  type="text"
                  name="code"
                  validate={{
                    pattern: {
                      value: '^([\\S]{1,10}$)*',
                      errorMessage: translate('entity.validation.pattern', { pattern: '^([\\S]{1,10}$)*' }),
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="produit-unite-nom">
                  <Translate contentKey="elbazarApp.produitUnite.nom">Nom</Translate>
                </Label>
                <AvField id="produit-unite-nom" type="text" name="nom" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/produit-unite" replace color="info">
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
  produitUniteEntity: storeState.produitUnite.entity,
  loading: storeState.produitUnite.loading,
  updating: storeState.produitUnite.updating,
  updateSuccess: storeState.produitUnite.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitUniteUpdate);
