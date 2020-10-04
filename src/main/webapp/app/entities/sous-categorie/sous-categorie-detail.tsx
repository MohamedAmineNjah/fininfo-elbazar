import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sous-categorie.reducer';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISousCategorieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SousCategorieDetail = (props: ISousCategorieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { sousCategorieEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.sousCategorie.detail.title">SousCategorie</Translate> [<b>{sousCategorieEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.sousCategorie.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="elbazarApp.sousCategorie.description">Description</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.description}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="elbazarApp.sousCategorie.position">Position</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.position}</dd>
          <dt>
            <span id="etat">
              <Translate contentKey="elbazarApp.sousCategorie.etat">Etat</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.etat ? 'true' : 'false'}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="elbazarApp.sousCategorie.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {sousCategorieEntity.image ? (
              <div>
                {sousCategorieEntity.imageContentType ? (
                  <a onClick={openFile(sousCategorieEntity.imageContentType, sousCategorieEntity.image)}>
                    <img
                      src={`data:${sousCategorieEntity.imageContentType};base64,${sousCategorieEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {sousCategorieEntity.imageContentType}, {byteSize(sousCategorieEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          {/* <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.sousCategorie.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>
            {sousCategorieEntity.creeLe ? (
              <TextFormat value={sousCategorieEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.sousCategorie.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.sousCategorie.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {sousCategorieEntity.modifieLe ? (
              <TextFormat value={sousCategorieEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.sousCategorie.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{sousCategorieEntity.modifiePar}</dd> */}
          <dt>
            <Translate contentKey="elbazarApp.sousCategorie.categorie">Categorie</Translate>
          </dt>
          <dd>{sousCategorieEntity.categorieNom ? sousCategorieEntity.categorieNom : ''}</dd>
        </dl>
        <Button tag={Link} to="/sous-categorie" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sous-categorie/${sousCategorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="star-and-crescent" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sousCategorie }: IRootState) => ({
  sousCategorieEntity: sousCategorie.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SousCategorieDetail);
