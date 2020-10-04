import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './gestion-fidelite.reducer';
import { IGestionFidelite } from 'app/shared/model/gestion-fidelite.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

export interface IGestionFideliteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const GestionFidelite = (props: IGestionFideliteProps) => {
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

  const { gestionFideliteList, match, loading, isAdmin } = props;
  return (
    <div>
      <h2 id="gestion-fidelite-heading">Formule de fidélité</h2>
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
                  placeholder={translate('elbazarApp.gestionFidelite.home.search')}
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
        {gestionFideliteList && gestionFideliteList.length > 0 ? (
          <Table responsive hover size="sm">
            <thead>
              <tr>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.points">Points</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.valeur">Valeur</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.silverMin">Silver Min</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.silverMax">Silver Max</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.goldMin">Gold Min</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.goldMax">Gold Max</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.platiniumMin">Platinium Min</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.platiniumMax">Platinium Max</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.devise">Devise</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.gestionFidelite.etat">Etat</Translate>
                </th>

                <th />
              </tr>
            </thead>
            <tbody>
              {gestionFideliteList.map((gestionFidelite, i) => (
                <tr key={`entity-${i}`}>
                  <td>{gestionFidelite.nom}</td>
                  <td>{gestionFidelite.points}</td>
                  <td>{gestionFidelite.valeur}</td>
                  <td>{gestionFidelite.silverMin}</td>
                  <td>{gestionFidelite.silverMax}</td>
                  <td>{gestionFidelite.goldMin}</td>
                  <td>{gestionFidelite.goldMax}</td>
                  <td>{gestionFidelite.platiniumMin}</td>
                  <td>{gestionFidelite.platiniumMax}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.Devise.${gestionFidelite.devise}`} />
                  </td>
                  <td>{gestionFidelite.etat ? 'Active' : 'Inactive'}</td>

                  <td className="text-right">
                    {isAdmin ?
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${gestionFidelite.id}/edit`} color="success" size="sm">
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
                <Translate contentKey="elbazarApp.gestionFidelite.home.notFound">No Gestion Fidelites found</Translate>
              </div>
            )
          )}
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  gestionFideliteList: storeState.gestionFidelite.entities,
  loading: storeState.gestionFidelite.loading,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GestionFidelite);
