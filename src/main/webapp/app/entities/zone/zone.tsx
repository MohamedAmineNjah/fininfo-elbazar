import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './zone.reducer';
import { IZone } from 'app/shared/model/zone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IZoneProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Zone = (props: IZoneProps) => {
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

  const { zoneList, match, loading } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="8">
          <h2 id="categorie-heading">
            Liste des zones
            <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Ajouter une nouvelle Zone
            </Link>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col sm="7">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.zone.home.search')}
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
        <Col md="6">
          <div className="table-responsive">
            {zoneList && zoneList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th>
                      <Translate contentKey="elbazarApp.zone.codeZone">Code Zone</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.zone.nom">Nom</Translate>
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {zoneList.map((zone, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{zone.codeZone}</td>
                      <td>{zone.nom}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${zone.id}/edit`} color="success" size="sm">
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
                  <Translate contentKey="elbazarApp.zone.home.notFound">No Zones found</Translate>
                </div>
              )
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ zone }: IRootState) => ({
  zoneList: zone.entities,
  loading: zone.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Zone);
