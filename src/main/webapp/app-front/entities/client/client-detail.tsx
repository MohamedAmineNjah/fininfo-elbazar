import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from '../../shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from '../../shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from '../../config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetail = (props: IClientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.client.detail.title">Client</Translate> [<b>{clientEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="civilite">
              <Translate contentKey="elbazarApp.client.civilite">Civilite</Translate>
            </span>
          </dt>
          <dd>{clientEntity.civilite}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="elbazarApp.client.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{clientEntity.prenom}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.client.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{clientEntity.nom}</dd>
          <dt>
            <span id="dateDeNaissance">
              <Translate contentKey="elbazarApp.client.dateDeNaissance">Date De Naissance</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.dateDeNaissance ? (
              <TextFormat value={clientEntity.dateDeNaissance} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="email">
              <Translate contentKey="elbazarApp.client.email">Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="elbazarApp.client.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{clientEntity.mobile}</dd>
          <dt>
            <span id="reglement">
              <Translate contentKey="elbazarApp.client.reglement">Reglement</Translate>
            </span>
          </dt>
          <dd>{clientEntity.reglement}</dd>
          <dt>
            <span id="etat">
              <Translate contentKey="elbazarApp.client.etat">Etat</Translate>
            </span>
          </dt>
          <dd>{clientEntity.etat ? 'true' : 'false'}</dd>
          <dt>
            <span id="inscription">
              <Translate contentKey="elbazarApp.client.inscription">Inscription</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.inscription ? <TextFormat value={clientEntity.inscription} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="derniereVisite">
              <Translate contentKey="elbazarApp.client.derniereVisite">Derniere Visite</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.derniereVisite ? (
              <TextFormat value={clientEntity.derniereVisite} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="totalAchat">
              <Translate contentKey="elbazarApp.client.totalAchat">Total Achat</Translate>
            </span>
          </dt>
          <dd>{clientEntity.totalAchat}</dd>
          <dt>
            <span id="profile">
              <Translate contentKey="elbazarApp.client.profile">Profile</Translate>
            </span>
          </dt>
          <dd>{clientEntity.profile}</dd>
          <dt>
            <span id="pointsFidelite">
              <Translate contentKey="elbazarApp.client.pointsFidelite">Points Fidelite</Translate>
            </span>
          </dt>
          <dd>{clientEntity.pointsFidelite}</dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.client.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>{clientEntity.creeLe ? <TextFormat value={clientEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.client.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{clientEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.client.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.modifieLe ? <TextFormat value={clientEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.client.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{clientEntity.modifiePar}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetail);
