import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './categorie.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategorieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CategorieDetail = (props: ICategorieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { categorieEntity } = props;
  return (
    <div>
      <div>
        <label className="form__label">Fiche catégorie</label>
      </div>
      <br />
      <Row className="justify-content-center">
        <Col md="6">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Détails catégorie</i>
              <Row>
                <Col md="6">
                  <AvGroup>
                    <dt>
                      <span id="nom">
                        <Translate contentKey="elbazarApp.categorie.nom">Nom</Translate>
                      </span>
                    </dt>
                    <dd>{categorieEntity.nom}</dd>
                    <dt>
                      <span id="description">
                        <Translate contentKey="elbazarApp.categorie.description">Description</Translate>
                      </span>
                    </dt>
                    <dd>{categorieEntity.description}</dd>
                    <dt>
                      <span id="position">
                        <Translate contentKey="elbazarApp.categorie.position">Position</Translate>
                      </span>
                    </dt>
                    <dd>{categorieEntity.position}</dd>
                    <dt>
                      <span id="etat">
                        <Translate contentKey="elbazarApp.categorie.etat">Etat</Translate>
                      </span>
                    </dt>
                    <dd>{categorieEntity.etat ? 'true' : 'false'}</dd>
                  </AvGroup>
                </Col>
                <Col md="6">
                  <AvGroup>
                    <dt>
                      <span id="image">
                        <Translate contentKey="elbazarApp.categorie.image">Image</Translate>
                      </span>
                    </dt>
                    <dd>
                      {categorieEntity.image ? (
                        <div>
                          {categorieEntity.imageContentType ? (
                            <a onClick={openFile(categorieEntity.imageContentType, categorieEntity.image)}>
                              <img
                                src={`data:${categorieEntity.imageContentType};base64,${categorieEntity.image}`}
                                style={{ maxHeight: '200px' }}
                              />
                            </a>
                          ) : null}
                        </div>
                      ) : null}
                    </dd>
                  </AvGroup>
                </Col>
              </Row>
            </AvGroup>
            <Row style={{ margin: 0 }}>
              <AvGroup className="justify-content-center">
                <Button tag={Link} to="/categorie" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />{' '}
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button tag={Link} to={`/categorie/${categorieEntity.id}/edit`} replace color="primary">
                  <FontAwesomeIcon icon="pencil-alt" />{' '}
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.edit">Edit</Translate>
                  </span>
                </Button>
              </AvGroup>
            </Row>
          </AvForm>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ categorie }: IRootState) => ({
  categorieEntity: categorie.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CategorieDetail);
