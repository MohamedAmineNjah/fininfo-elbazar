import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table, Badge } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './categorie.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICategorieProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Categorie = (props: ICategorieProps) => {
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

  const { categorieList, match, loading } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="8">
          <h2 id="categorie-heading">
            Liste des catégories
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="elbazarApp.categorie.home.createLabel">Create new Categorie</Translate>
            </Link>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col sm="8">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.categorie.home.search')}
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
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <div className="table-responsive">
            {categorieList && categorieList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th>
                      <Translate contentKey="global.field.id">ID</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.categorie.nom">Nom</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.categorie.description">Description</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.categorie.position">Position</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.categorie.etat">Etat</Translate>
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {categorieList.map((categorie, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{categorie.id}</td>
                      <td>{categorie.nom}</td>
                      <td>{categorie.description}</td>
                      <td>{categorie.position}</td>
                      <td>
                        {' '}
                        {categorie.etat ? (
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
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${categorie.id}/edit`} color="success" size="sm">
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
                  <Translate contentKey="elbazarApp.categorie.home.notFound">No Categories found</Translate>
                </div>
              )
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ categorie }: IRootState) => ({
  categorieList: categorie.entities,
  loading: categorie.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Categorie);
