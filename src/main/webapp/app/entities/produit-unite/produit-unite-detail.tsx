import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './produit-unite.reducer';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProduitUniteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProduitUniteDetail = (props: IProduitUniteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { produitUniteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.produitUnite.detail.title">ProduitUnite</Translate> [<b>{produitUniteEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="code">
              <Translate contentKey="elbazarApp.produitUnite.code">Code</Translate>
            </span>
          </dt>
          <dd>{produitUniteEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.produitUnite.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{produitUniteEntity.nom}</dd>
        </dl>
        <Button tag={Link} to="/produit-unite" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/produit-unite/${produitUniteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ produitUnite }: IRootState) => ({
  produitUniteEntity: produitUnite.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitUniteDetail);
