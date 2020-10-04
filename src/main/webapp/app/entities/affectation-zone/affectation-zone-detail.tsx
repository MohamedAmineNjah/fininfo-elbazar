import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './affectation-zone.reducer';
import { IAffectationZone } from 'app/shared/model/affectation-zone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAffectationZoneDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AffectationZoneDetail = (props: IAffectationZoneDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { affectationZoneEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.affectationZone.detail.title">AffectationZone</Translate> [<b>{affectationZoneEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gouvernorat">
              <Translate contentKey="elbazarApp.affectationZone.gouvernorat">Gouvernorat</Translate>
            </span>
          </dt>
          <dd>{affectationZoneEntity.gouvernorat}</dd>
          <dt>
            <span id="ville">
              <Translate contentKey="elbazarApp.affectationZone.ville">Ville</Translate>
            </span>
          </dt>
          <dd>{affectationZoneEntity.ville}</dd>
          <dt>
            <span id="localite">
              <Translate contentKey="elbazarApp.affectationZone.localite">Localite</Translate>
            </span>
          </dt>
          <dd>{affectationZoneEntity.localite}</dd>
          <dt>
            <span id="codePostal">
              <Translate contentKey="elbazarApp.affectationZone.codePostal">Code Postal</Translate>
            </span>
          </dt>
          <dd>{affectationZoneEntity.codePostal}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.affectationZone.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {affectationZoneEntity.modifieLe ? (
              <TextFormat value={affectationZoneEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.affectationZone.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{affectationZoneEntity.modifiePar}</dd>
          <dt>
            <Translate contentKey="elbazarApp.affectationZone.zone">Zone</Translate>
          </dt>
          <dd>{affectationZoneEntity.zoneNom ? affectationZoneEntity.zoneNom : ''}</dd>
        </dl>
        <Button tag={Link} to="/affectation-zone" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/affectation-zone/${affectationZoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ affectationZone }: IRootState) => ({
  affectationZoneEntity: affectationZone.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AffectationZoneDetail);
