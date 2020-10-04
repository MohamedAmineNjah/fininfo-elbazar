import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gestion-fidelite.reducer';
import { IGestionFidelite } from 'app/shared/model/gestion-fidelite.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGestionFideliteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GestionFideliteDetail = (props: IGestionFideliteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gestionFideliteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.gestionFidelite.detail.title">GestionFidelite</Translate> [<b>{gestionFideliteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.gestionFidelite.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.nom}</dd>
          <dt>
            <span id="points">
              <Translate contentKey="elbazarApp.gestionFidelite.points">Points</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.points}</dd>
          <dt>
            <span id="valeur">
              <Translate contentKey="elbazarApp.gestionFidelite.valeur">Valeur</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.valeur}</dd>
          <dt>
            <span id="silverMin">
              <Translate contentKey="elbazarApp.gestionFidelite.silverMin">Silver Min</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.silverMin}</dd>
          <dt>
            <span id="silverMax">
              <Translate contentKey="elbazarApp.gestionFidelite.silverMax">Silver Max</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.silverMax}</dd>
          <dt>
            <span id="goldMin">
              <Translate contentKey="elbazarApp.gestionFidelite.goldMin">Gold Min</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.goldMin}</dd>
          <dt>
            <span id="goldMax">
              <Translate contentKey="elbazarApp.gestionFidelite.goldMax">Gold Max</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.goldMax}</dd>
          <dt>
            <span id="platiniumMin">
              <Translate contentKey="elbazarApp.gestionFidelite.platiniumMin">Platinium Min</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.platiniumMin}</dd>
          <dt>
            <span id="platiniumMax">
              <Translate contentKey="elbazarApp.gestionFidelite.platiniumMax">Platinium Max</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.platiniumMax}</dd>
          <dt>
            <span id="devise">
              <Translate contentKey="elbazarApp.gestionFidelite.devise">Devise</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.devise}</dd>
          <dt>
            <span id="etat">
              <Translate contentKey="elbazarApp.gestionFidelite.etat">Etat</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.etat ? 'true' : 'false'}</dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.gestionFidelite.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>
            {gestionFideliteEntity.creeLe ? (
              <TextFormat value={gestionFideliteEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.gestionFidelite.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.gestionFidelite.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {gestionFideliteEntity.modifieLe ? (
              <TextFormat value={gestionFideliteEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.gestionFidelite.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{gestionFideliteEntity.modifiePar}</dd>
        </dl>
        <Button tag={Link} to="/gestion-fidelite" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gestion-fidelite/${gestionFideliteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gestionFidelite }: IRootState) => ({
  gestionFideliteEntity: gestionFidelite.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GestionFideliteDetail);
