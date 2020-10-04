import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './commande-lignes.reducer';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICommandeLignesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommandeLignesDetail = (props: ICommandeLignesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { commandeLignesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.commandeLignes.detail.title">CommandeLignes</Translate> [<b>{commandeLignesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="quantite">
              <Translate contentKey="elbazarApp.commandeLignes.quantite">Quantite</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.quantite}</dd>
          <dt>
            <span id="prixHT">
              <Translate contentKey="elbazarApp.commandeLignes.prixHT">Prix HT</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.prixHT}</dd>
          <dt>
            <span id="remise">
              <Translate contentKey="elbazarApp.commandeLignes.remise">Remise</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.remise}</dd>
          <dt>
            <span id="tva">
              <Translate contentKey="elbazarApp.commandeLignes.tva">Tva</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.tva}</dd>
          <dt>
            <span id="prixTTC">
              <Translate contentKey="elbazarApp.commandeLignes.prixTTC">Prix TTC</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.prixTTC}</dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.commandeLignes.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>
            {commandeLignesEntity.creeLe ? (
              <TextFormat value={commandeLignesEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.commandeLignes.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.commandeLignes.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {commandeLignesEntity.modifieLe ? (
              <TextFormat value={commandeLignesEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.commandeLignes.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{commandeLignesEntity.modifiePar}</dd>
          <dt>
            <Translate contentKey="elbazarApp.commandeLignes.refCommande">Ref Commande</Translate>
          </dt>
          <dd>{commandeLignesEntity.refCommandeReference ? commandeLignesEntity.refCommandeReference : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.commandeLignes.refProduit">Ref Produit</Translate>
          </dt>
          <dd>{commandeLignesEntity.refProduitReference ? commandeLignesEntity.refProduitReference : ''}</dd>
        </dl>
        <Button tag={Link} to="/commande-lignes" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commande-lignes/${commandeLignesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ commandeLignes }: IRootState) => ({
  commandeLignesEntity: commandeLignes.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeLignesDetail);
