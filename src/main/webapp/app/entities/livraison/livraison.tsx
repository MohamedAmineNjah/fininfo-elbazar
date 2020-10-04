import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './livraison.reducer';
import { ILivraison } from 'app/shared/model/livraison.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface ILivraisonProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Livraison = (props: ILivraisonProps) => {
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

  const { livraisonList, match, loading, isAdmin } = props;
  return (
    <div>
      <h2 id="livraison-heading">
        {' '}
        Formules de livraison
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Paramétrer une nouvelle Formule
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.livraison.home.search')}
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
      <div className="table-responsive">
        {livraisonList && livraisonList.length > 0 ? (
          <Table responsive hover size="sm">
            <thead>
              <tr style={{ textAlign: 'center' }}>
                <th> ID Formule</th>
                <th> Profile client</th>

                <th>
                  <Translate contentKey="elbazarApp.livraison.zone">Zone</Translate>
                </th>

                <th>Val. Min (TND)</th>
                <th>Val. Max (TND)</th>
                <th> </th>

                <th>Frais de livraison (TND)</th>
                <th> Délai de livraison (Hr)</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {livraisonList.map((livraison, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${livraison.id}`} color="link" size="sm">
                      {livraison.id}
                    </Button>
                  </td>
                  <td style={{ textAlign: 'center' }}>
                    <Translate contentKey={`elbazarApp.ProfileClient.${livraison.categorieClient}`} />
                  </td>

                  <td style={{ textAlign: 'center' }}>
                    {livraison.zoneNom ? <Link to={`zone/${livraison.zoneId}`}>{livraison.zoneNom}</Link> : ''}
                  </td>

                  <td style={{ textAlign: 'center' }}>{livraison.valeurMin}</td>
                  <td style={{ textAlign: 'center' }}>{livraison.valeurMax}</td>

                  <td style={{ backgroundColor: '#CDCDCD' }}></td>
                  <td style={{ textAlign: 'center' }}>{livraison.frais}</td>

                  <td style={{ textAlign: 'center' }}>{livraison.date}</td>

                  <td className="text-right">
                  {isAdmin ?
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${livraison.id}/edit`} color="success" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      </div> : null}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="elbazarApp.livraison.home.notFound">No Livraisons found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  livraisonList: storeState.livraison.entities,
  loading: storeState.livraison.loading,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Livraison);
