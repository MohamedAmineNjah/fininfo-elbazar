import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './info-societe.reducer';
import { IInfoSociete } from 'app/shared/model/info-societe.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInfoSocieteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InfoSocieteDetail = (props: IInfoSocieteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { infoSocieteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.infoSociete.detail.title">InfoSociete</Translate> [<b>{infoSocieteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nomSociete">
              <Translate contentKey="elbazarApp.infoSociete.nomSociete">Nom Societe</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.nomSociete}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="elbazarApp.infoSociete.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.adresse}</dd>
          <dt>
            <span id="tel1">
              <Translate contentKey="elbazarApp.infoSociete.tel1">Tel 1</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.tel1}</dd>
          <dt>
            <span id="tel2">
              <Translate contentKey="elbazarApp.infoSociete.tel2">Tel 2</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.tel2}</dd>
          <dt>
            <span id="tel3">
              <Translate contentKey="elbazarApp.infoSociete.tel3">Tel 3</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.tel3}</dd>
          <dt>
            <span id="email1">
              <Translate contentKey="elbazarApp.infoSociete.email1">Email 1</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.email1}</dd>
          <dt>
            <span id="email2">
              <Translate contentKey="elbazarApp.infoSociete.email2">Email 2</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.email2}</dd>

          <dt>
            <span id="facebook">
              <Translate contentKey="elbazarApp.infoSociete.facebook">Facebook</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.facebook}</dd>
          <dt>
            <span id="youtube">
              <Translate contentKey="elbazarApp.infoSociete.youtube">Youtube</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.youtube}</dd>
          <dt>
            <span id="instagram">
              <Translate contentKey="elbazarApp.infoSociete.instagram">Instagram</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.instagram}</dd>
          <dt>
            <span id="twitter">
              <Translate contentKey="elbazarApp.infoSociete.twitter">Twitter</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.twitter}</dd>
          <dt>
            <span id="tiktok">
              <Translate contentKey="elbazarApp.infoSociete.tiktok">Tiktok</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.tiktok}</dd>
          <dt>
            <span id="matriculeFiscal">
              <Translate contentKey="elbazarApp.infoSociete.matriculeFiscal">Matricule Fiscal</Translate>
            </span>
          </dt>
          <dd>{infoSocieteEntity.matriculeFiscal}</dd>
        </dl>
        <Button tag={Link} to="/info-societe" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/info-societe/${infoSocieteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ infoSociete }: IRootState) => ({
  infoSocieteEntity: infoSociete.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InfoSocieteDetail);
