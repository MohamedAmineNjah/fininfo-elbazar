import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, CardTitle, CardText, Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import classnames from 'classnames';
import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, getFilteredEntities } from './affectation-zone.reducer';
import { IAffectationZone } from 'app/shared/model/affectation-zone.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { getFilterRequestString, handleNumberSelectChanged, handleFilterIDChange } from 'app/shared/util/entity-filter-services';
import { Select, MenuItem, TextField } from '@material-ui/core';

export interface IAffectationZoneProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const AffectationZone = (props: IAffectationZoneProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const [filter, setfilter] = useState('');

  const filterDefaultValue = {
    id: { fieldName: 'id', selecType: '.equals', inputValue: '' },
    gouvernorat: { fieldName: 'gouvernorat', selecType: '.contains', inputValue: '' },
    ville: { fieldName: 'ville', selecType: '.contains', inputValue: '' },
    localite: { fieldName: 'localite', selecType: '.contains', inputValue: '' },
    codePostal: { fieldName: 'codePostal', selecType: '.equals', inputValue: '' },
    zoneNom: { fieldName: 'zoneNom', selecType: '.contains', inputValue: '' },
  };

  const [filterValues, setfilterValues] = useState(filterDefaultValue);


  const getAllEntities = () => {
    if (filter) {
      props.getFilteredEntities(filter, paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const clear = () => {
    setfilterValues(filterDefaultValue);
    setfilter('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  const filterData = () => {
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    const filtervalue = getFilterRequestString(filterValues);
    props.getFilteredEntities(filtervalue, paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    setfilter(filtervalue);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { affectationZoneList, match, loading, totalItems } = props;

  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="8">
          <h2 id="affectation-zone-heading">
            {' '}
            Liste des affectations par zone

          </h2>
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md="8">
          <div className="table-responsive">
            {affectationZoneList && affectationZoneList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th className="hand" >
                      <div style={{ display: 'block' }} onClick={sort('id')}>
                        <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <Select
                          id="comparator-select"
                          name="id"
                          value={filterValues.id.selecType}
                          onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                        >
                          <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                          <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                          <MenuItem key={2} value={'.equals'}>=</MenuItem>
                        </Select>
                        <TextField
                          id="filter-id"
                          name="id"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.id.inputValue}
                          style={{ width: 80 }}
                        />
                      </div>
                    </th>
                    <th className="hand">
                      <div style={{ display: 'block' }} onClick={sort('gouvernorat')}>
                        <Translate contentKey="elbazarApp.affectationZone.gouvernorat">Gouvernorat</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <TextField
                          id="filter-id"
                          name="gouvernorat"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.gouvernorat.inputValue}
                        />
                      </div>
                    </th>
                    <th className="hand" >
                      <div style={{ display: 'block' }} onClick={sort('ville')}>
                        <Translate contentKey="elbazarApp.affectationZone.ville">Ville</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <TextField
                          id="filter-id"
                          name="ville"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.ville.inputValue}
                        />
                      </div>
                    </th>
                    <th className="hand" >
                      <div style={{ display: 'block' }} onClick={sort('localite')}>
                        <Translate contentKey="elbazarApp.affectationZone.localite">Localite</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <TextField
                          id="filter-id"
                          name="localite"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.localite.inputValue}
                        />
                      </div>
                    </th>
                    <th className="hand" >
                      <div style={{ display: 'block' }} onClick={sort('codePostal')}>
                        <Translate contentKey="elbazarApp.affectationZone.codePostal">Code Postal</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <Select
                          id="comparator-select"
                          name="codePostal"
                          value={filterValues.codePostal.selecType}
                          onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                        >
                          <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                          <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                          <MenuItem key={2} value={'.equals'}>=</MenuItem>
                        </Select>
                        <TextField
                          id="filter-id"
                          name="codePostal"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.codePostal.inputValue}
                          style={{ width: 80 }}
                        />
                      </div>
                    </th>
                    <th>
                      <div style={{ display: 'block' }} >
                        <Translate contentKey="elbazarApp.affectationZone.zone">Zone</Translate> <FontAwesomeIcon icon="sort" />
                      </div>
                      <div style={{ display: 'block' }}>
                        <TextField
                          id="filter-id"
                          name="zoneNom"
                          onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                          value={filterValues.zoneNom.inputValue}
                        />
                      </div>
                    </th>
                    <th>
                      <Button type="reset" className="input-group-addon" onClick={filterData}>
                        <FontAwesomeIcon icon="search" />
                      </Button>
                      <Button type="reset" className="input-group-addon" onClick={clear}>
                        <FontAwesomeIcon icon="trash" />
                      </Button>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {affectationZoneList.map((affectationZone, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${affectationZone.id}`} color="link" size="sm">
                          {affectationZone.id}
                        </Button>
                      </td>
                      <td>{affectationZone.gouvernorat}</td>
                      <td>{affectationZone.ville}</td>
                      <td>{affectationZone.localite}</td>
                      <td>{affectationZone.codePostal}</td>

                      <td>{affectationZone.zoneNom ? <Link to={`zone/${affectationZone.zoneId}`}>{affectationZone.zoneNom}</Link> : ''}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button
                            tag={Link}
                            to={`${match.url}/${affectationZone.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="success"
                            size="sm"
                          >
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
                    <Translate contentKey="elbazarApp.affectationZone.home.notFound">No Affectation Zones found</Translate>
                  </div>
                )
              )}
          </div>
          {props.totalItems ? (
            <div className={affectationZoneList && affectationZoneList.length > 0 ? '' : 'd-none'}>
              <Row className="justify-content-center">
                <JhiItemCount
                  page={paginationState.activePage}
                  total={totalItems}
                  itemsPerPage={paginationState.itemsPerPage}
                  i18nEnabled
                />
              </Row>
              <Row className="justify-content-center">
                <JhiPagination
                  activePage={paginationState.activePage}
                  onSelect={handlePagination}
                  maxButtons={5}
                  itemsPerPage={paginationState.itemsPerPage}
                  totalItems={props.totalItems}
                />
              </Row>
            </div>
          ) : (
              ''
            )}
        </Col>{' '}
      </Row>
    </div>
  );
};

const mapStateToProps = ({ affectationZone }: IRootState) => ({
  affectationZoneList: affectationZone.entities,
  loading: affectationZone.loading,
  totalItems: affectationZone.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AffectationZone);
