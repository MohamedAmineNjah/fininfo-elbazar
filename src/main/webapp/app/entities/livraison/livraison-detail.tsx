import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './livraison.reducer';
import { ILivraison } from 'app/shared/model/livraison.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILivraisonDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LivraisonDetail = (props: ILivraisonDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { livraisonEntity } = props;
  return (
    <div>
      <div>
        <label className="form__label">Fiche livraison</label>
      </div>
      <br />
      <Row className="justify-content-center">
        <Col md="8">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Détails livraison</i>
              <Row>
                <Col md="6">
                  <dt>
                    <span id="categorieClient">
                      <Translate contentKey="elbazarApp.livraison.categorieClient">Categorie Client</Translate>
                    </span>
                  </dt>
                  <dd>{livraisonEntity.categorieClient}</dd>
                  <dt>
                    <Translate contentKey="elbazarApp.livraison.zone">Zone</Translate>
                  </dt>
                  <dd>{livraisonEntity.zoneNom ? livraisonEntity.zoneNom : ''}</dd>
                  <dt>
                    <span id="frais">Frais de la livraison</span>
                  </dt>
                  <dd>{livraisonEntity.frais} TND</dd>
                </Col>
                <Col md="6">
                  <dt>
                    <span id="valeurMin">
                      <Translate contentKey="elbazarApp.livraison.valeurMin">Valeur Minimum de la commande</Translate>
                    </span>
                  </dt>
                  <dd>{livraisonEntity.valeurMin} TND</dd>
                  <dt>
                    <span id="valeurMax">
                      <Translate contentKey="elbazarApp.livraison.valeurMax">Valeur Maximum de la commande</Translate>
                    </span>
                  </dt>
                  <dd>{livraisonEntity.valeurMax} TND</dd>
                  <dt>
                    <span id="date">Délai de la livraison</span>
                  </dt>
                  <dd>{livraisonEntity.date} Hr</dd>
                </Col>
              </Row>
            </AvGroup>
          </AvForm>
          <Button tag={Link} to="/livraison" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/livraison/${livraisonEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ livraison }: IRootState) => ({
  livraisonEntity: livraison.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LivraisonDetail);
