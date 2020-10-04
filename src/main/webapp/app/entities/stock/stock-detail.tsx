import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './stock.reducer';
import { IStock } from 'app/shared/model/stock.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStockDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockDetail = (props: IStockDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { stockEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.stock.detail.title">Stock</Translate> [<b>{stockEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="stockReserve">
              <Translate contentKey="elbazarApp.stock.stockReserve">Stock Reserve</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockReserve}</dd>
          <dt>
            <span id="stockCommande">
              <Translate contentKey="elbazarApp.stock.stockCommande">Stock Commande</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockCommande}</dd>
          <dt>
            <span id="stockPhysique">
              <Translate contentKey="elbazarApp.stock.stockPhysique">Stock Physique</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockPhysique}</dd>
          <dt>
            <span id="stockDisponible">
              <Translate contentKey="elbazarApp.stock.stockDisponible">Stock Disponible</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockDisponible}</dd>
          <dt>
            <span id="stockMinimum">
              <Translate contentKey="elbazarApp.stock.stockMinimum">Stock Minimum</Translate>
            </span>
          </dt>
          <dd>{stockEntity.stockMinimum}</dd>
          <dt>
            <span id="derniereEntre">
              <Translate contentKey="elbazarApp.stock.derniereEntre">Derniere Entre</Translate>
            </span>
          </dt>
          <dd>
            {stockEntity.derniereEntre ? <TextFormat value={stockEntity.derniereEntre} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="derniereSortie">
              <Translate contentKey="elbazarApp.stock.derniereSortie">Derniere Sortie</Translate>
            </span>
          </dt>
          <dd>
            {stockEntity.derniereSortie ? (
              <TextFormat value={stockEntity.derniereSortie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="alerteStock">
              <Translate contentKey="elbazarApp.stock.alerteStock">Alerte Stock</Translate>
            </span>
          </dt>
          <dd>{stockEntity.alerteStock ? 'true' : 'false'}</dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.stock.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>{stockEntity.creeLe ? <TextFormat value={stockEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.stock.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{stockEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.stock.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>{stockEntity.modifieLe ? <TextFormat value={stockEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.stock.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{stockEntity.modifiePar}</dd>
          <dt>
            <Translate contentKey="elbazarApp.stock.refProduit">Ref Produit</Translate>
          </dt>
          <dd>{stockEntity.refProduitReference ? stockEntity.refProduitReference : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.stock.idCategorie">Id Categorie</Translate>
          </dt>
          <dd>{stockEntity.idCategorieNom ? stockEntity.idCategorieNom : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.stock.idSousCategorie">Id Sous Categorie</Translate>
          </dt>
          <dd>{stockEntity.idSousCategorieNom ? stockEntity.idSousCategorieNom : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock/${stockEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ stock }: IRootState) => ({
  stockEntity: stock.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockDetail);
