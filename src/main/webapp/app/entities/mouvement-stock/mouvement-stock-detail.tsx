import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mouvement-stock.reducer';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMouvementStockDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MouvementStockDetail = (props: IMouvementStockDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mouvementStockEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.mouvementStock.detail.title">MouvementStock</Translate> [<b>{mouvementStockEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="elbazarApp.mouvementStock.type">Type</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.type}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="elbazarApp.mouvementStock.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {mouvementStockEntity.date ? <TextFormat value={mouvementStockEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="sens">
              <Translate contentKey="elbazarApp.mouvementStock.sens">Sens</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.sens}</dd>
          <dt>
            <span id="quantite">
              <Translate contentKey="elbazarApp.mouvementStock.quantite">Quantite</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.quantite}</dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.mouvementStock.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>
            {mouvementStockEntity.creeLe ? (
              <TextFormat value={mouvementStockEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.mouvementStock.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.mouvementStock.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {mouvementStockEntity.modifieLe ? (
              <TextFormat value={mouvementStockEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.mouvementStock.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.modifiePar}</dd>
          <dt>
            <span id="reference">
              <Translate contentKey="elbazarApp.mouvementStock.reference">Reference</Translate>
            </span>
          </dt>
          <dd>{mouvementStockEntity.reference}</dd>
          <dt>
            <Translate contentKey="elbazarApp.mouvementStock.refProduit">Ref Produit</Translate>
          </dt>
          <dd>{mouvementStockEntity.refProduitReference ? mouvementStockEntity.refProduitReference : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.mouvementStock.refCommande">Ref Commande</Translate>
          </dt>
          <dd>{mouvementStockEntity.refCommandeReference ? mouvementStockEntity.refCommandeReference : ''}</dd>
        </dl>
        <Button tag={Link} to="/mouvement-stock" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mouvement-stock/${mouvementStockEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mouvementStock }: IRootState) => ({
  mouvementStockEntity: mouvementStock.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MouvementStockDetail);
