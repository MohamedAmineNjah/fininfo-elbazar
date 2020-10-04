import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './produit-unite.reducer';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProduitUniteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProduitUnite = (props: IProduitUniteProps) => {
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

  const { produitUniteList, match, loading } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="8">
          <h2 id="affectation-zone-heading">
            {' '}
            Liste des unités produit
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp; créer une nouvelle unité produit
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
                  placeholder={translate('elbazarApp.produitUnite.home.search')}
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
            {produitUniteList && produitUniteList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th>
                      <Translate contentKey="global.field.id">ID</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.produitUnite.code">Code</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.produitUnite.nom">Nom</Translate>
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {produitUniteList.map((produitUnite, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{produitUnite.id}</td>
                      <td>{produitUnite.code}</td>
                      <td>{produitUnite.nom}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${produitUnite.id}/edit`} color="success" size="sm">
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
                  <Translate contentKey="elbazarApp.produitUnite.home.notFound">No Produit Unites found</Translate>
                </div>
              )
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ produitUnite }: IRootState) => ({
  produitUniteList: produitUnite.entities,
  loading: produitUnite.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitUnite);
