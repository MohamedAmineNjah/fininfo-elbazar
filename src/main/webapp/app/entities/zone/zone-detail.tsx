import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './zone.reducer';
import { IZone } from 'app/shared/model/zone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IZoneDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ZoneDetail = (props: IZoneDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { zoneEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.zone.detail.title">Zone</Translate> [<b>{zoneEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="codeZone">
              <Translate contentKey="elbazarApp.zone.codeZone">Code Zone</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.codeZone}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.zone.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.nom}</dd>
          {/* <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.zone.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.creeLe ? <TextFormat value={zoneEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.zone.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.zone.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.modifieLe ? <TextFormat value={zoneEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd> */}
          {/* <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.zone.modifiePar">Modifie Par</Translate>
            </span>
          </dt> */}
          <dd>{zoneEntity.modifiePar}</dd>
        </dl>
        <Button tag={Link} to="/zone" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zone/${zoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ zone }: IRootState) => ({
  zoneEntity: zone.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ZoneDetail);
