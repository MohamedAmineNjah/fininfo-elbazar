import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table, Badge } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './sous-categorie.reducer';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISousCategorieProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SousCategorie = (props: ISousCategorieProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { sousCategorieList, match, loading } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="9">
          <h2 id="sous-categorie-heading">
            Liste des sous catégories
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp;Créer une nouvelle sous catégorie
            </Link>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col sm="9">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.sousCategorie.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
          <div className="table-responsive">
            {sousCategorieList && sousCategorieList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th>
                      <Translate contentKey="global.field.id">ID</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.sousCategorie.nom">Nom</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.sousCategorie.description">Description</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.sousCategorie.position">Position</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.sousCategorie.etat">Etat</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.sousCategorie.categorie">Categorie</Translate>
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {sousCategorieList.map((sousCategorie, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{sousCategorie.id}</td>
                      <td>{sousCategorie.nom}</td>
                      <td>{sousCategorie.description}</td>
                      <td>{sousCategorie.position}</td>
                      <td>
                        {sousCategorie.etat ? (
                          <h5>
                            <i>
                              <Badge color="success">Actif</Badge>
                            </i>
                          </h5>
                        ) : (
                          <h5>
                            <i>
                              <Badge color="danger">Inactif</Badge>
                            </i>
                          </h5>
                        )}
                      </td>
                      <td>
                        {sousCategorie.categorieNom ? (
                          <Link to={`categorie/${sousCategorie.categorieId}`}>{sousCategorie.categorieNom}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${sousCategorie.id}/edit`} color="success" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="elbazarApp.sousCategorie.home.notFound">No Sous Categories found</Translate>
                </div>
              )
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ sousCategorie }: IRootState) => ({
  sousCategorieList: sousCategorie.entities,
  loading: sousCategorie.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SousCategorie);
