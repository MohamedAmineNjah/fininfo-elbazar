import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table, Badge } from 'reactstrap';
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

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, getFilteredEntities } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import {
  handleNumberSelectChanged,
  handleFilterIDChange,
  handleSelectValueChanged,
  handleFilterChangeChecked,
  getFilterRequestString,
} from 'app/shared/util/entity-filter-services';
import { TextField, MenuItem, Select, FormControlLabel, Checkbox } from '@material-ui/core';

export interface IClientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Client = (props: IClientProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const [filter, setfilter] = useState('');

  const filterDefaultValue = {
    id: { fieldName: 'id', selecType: '.equals', inputValue: '' },
    civilite: { fieldName: 'civilite', selecType: '.equals', inputValue: '' },
    prenom: { fieldName: 'prenom', selecType: '.contains', inputValue: '' },
    nom: { fieldName: 'nom', selecType: '.contains', inputValue: '' },
    email: { fieldName: 'email', selecType: '.contains', inputValue: '' },
    mobile: { fieldName: 'mobile', selecType: '.equals', inputValue: '' },
    reglement: { fieldName: 'reglement', selecType: '.equals', inputValue: '' },
    etat: { fieldName: 'etat', selecType: '.equals', inputValue: '' },
    totalAchat: { fieldName: 'totalAchat', selecType: '.equals', inputValue: '' },
    profile: { fieldName: 'profile', selecType: '.equals', inputValue: '' },
    pointsFidelite: { fieldName: 'pointsFidelite', selecType: '.equals', inputValue: '' },
  };

  const [filterValues, setfilterValues] = useState(filterDefaultValue);

  const getAllEntities = () => {
    if (filter) {
      props.getFilteredEntities(
        filter,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
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
    props.getFilteredEntities(
      filtervalue,
      paginationState.activePage - 1,
      paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`
    );
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

  const { clientList, match, loading, totalItems } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col sm="12">
          <h2 id="client-heading"> Liste des clients</h2>
        </Col>
      </Row>
      <div className="table-responsive">
        <Row className="justify-content-center">
          <Col sm="12">
            <Table hover size="sm" responsive>
              <thead>
                <tr>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="id"
                        value={filterValues.id.selecType}
                        onChange={e => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                      >
                        <MenuItem key={0} value={'.greaterThan'}>
                          &gt;
                        </MenuItem>
                        <MenuItem key={1} value={'.lessThan'}>
                          &lt;
                        </MenuItem>
                        <MenuItem key={2} value={'.equals'}>
                          =
                        </MenuItem>
                      </Select>
                      <TextField
                        id="filter-id"
                        name="id"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.id.inputValue}
                        style={{ width: 80 }}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('civilite')}>
                      <Translate contentKey="elbazarApp.client.civilite">Civilite</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="civilite"
                        value={filterValues.civilite.inputValue}
                        onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                        style={{ width: 80 }}
                      >
                        <MenuItem key={0} value={'M'}>
                          M
                        </MenuItem>
                        <MenuItem key={1} value={'MMe'}>
                          Mme
                        </MenuItem>
                      </Select>
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('prenom')}>
                      <Translate contentKey="elbazarApp.client.prenom">Prenom</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <TextField
                        id="filter-id"
                        name="prenom"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.prenom.inputValue}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('nom')}>
                      <Translate contentKey="elbazarApp.client.nom">Nom</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <TextField
                        id="filter-id"
                        name="nom"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.nom.inputValue}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('email')}>
                      <Translate contentKey="elbazarApp.client.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <TextField
                        id="filter-id"
                        name="email"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.email.inputValue}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('mobile')}>
                      <Translate contentKey="elbazarApp.client.mobile">Mobile</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <TextField
                        id="filter-id"
                        name="mobile"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.mobile.inputValue}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('reglement')}>
                      <Translate contentKey="elbazarApp.client.reglement">Reglement</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="reglement"
                        value={filterValues.reglement.inputValue}
                        onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                        style={{ width: 80 }}
                      >
                        <MenuItem key={0} value={'CarteBancaire'}>
                          CarteBancaire
                        </MenuItem>
                        <MenuItem key={1} value={'Cash'}>
                          Cash
                        </MenuItem>
                        <MenuItem key={1} value={'Cheque'}>
                          Cheque
                        </MenuItem>
                      </Select>
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('etat')}>
                      <Translate contentKey="elbazarApp.client.etat">Etat</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="etat"
                        value={filterValues.etat.inputValue}
                        onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                        style={{ width: 120 }}
                      >
                        <MenuItem key={0} value={'true'}>
                          Actif
                        </MenuItem>
                        <MenuItem key={1} value={'false'}>
                          Inactif
                        </MenuItem>
                      </Select>
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('totalAchat')}>
                      <Translate contentKey="elbazarApp.client.totalAchat">Total Achat</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="totalAchat"
                        value={filterValues.totalAchat.selecType}
                        onChange={e => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                      >
                        <MenuItem key={0} value={'.greaterThan'}>
                          &gt;
                        </MenuItem>
                        <MenuItem key={1} value={'.lessThan'}>
                          &lt;
                        </MenuItem>
                        <MenuItem key={2} value={'.equals'}>
                          =
                        </MenuItem>
                      </Select>
                      <TextField
                        id="filter-id"
                        name="totalAchat"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.totalAchat.inputValue}
                        style={{ width: 80 }}
                      />
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('profile')}>
                      <Translate contentKey="elbazarApp.client.profile">Profile</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="profile"
                        value={filterValues.profile.inputValue}
                        onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                        style={{ width: 100 }}
                      >
                        <MenuItem key={0} value={'Silver'}>
                          Silver
                        </MenuItem>
                        <MenuItem key={1} value={'Gold'}>
                          Gold
                        </MenuItem>
                        <MenuItem key={1} value={'Platinium'}>
                          Platinium
                        </MenuItem>
                      </Select>
                    </div>
                  </th>
                  <th className="hand">
                    <div style={{ display: 'block' }} onClick={sort('pointsFidelite')}>
                      <Translate contentKey="elbazarApp.client.pointsFidelite">Points Fidelite</Translate> <FontAwesomeIcon icon="sort" />
                    </div>
                    <div style={{ display: 'block' }}>
                      <Select
                        id="comparator-select"
                        name="pointsFidelite"
                        value={filterValues.pointsFidelite.selecType}
                        onChange={e => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                      >
                        <MenuItem key={0} value={'.greaterThan'}>
                          &gt;
                        </MenuItem>
                        <MenuItem key={1} value={'.lessThan'}>
                          &lt;
                        </MenuItem>
                        <MenuItem key={2} value={'.equals'}>
                          =
                        </MenuItem>
                      </Select>
                      <TextField
                        id="filter-id"
                        name="pointsFidelite"
                        onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                        value={filterValues.pointsFidelite.inputValue}
                        style={{ width: 80 }}
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
              {clientList && clientList.length > 0 ? (
                <tbody>
                  {clientList.map((client, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{client.id}</td>
                      <td>
                        <Translate contentKey={`elbazarApp.Civilite.${client.civilite}`} />
                      </td>
                      <td>{client.prenom}</td>
                      <td>{client.nom}</td>

                      <td>{client.email}</td>
                      <td>{client.mobile}</td>
                      <td>
                        <Translate contentKey={`elbazarApp.RegMod.${client.reglement}`} />
                      </td>
                      <td>{client.etat ? 'Actif' : 'Inactif'}</td>

                      <td>{client.totalAchat}</td>

                      <td>
                        {client.profile === 'Silver' ? (
                          <h5>
                            <i>
                              <Badge color="secondary">Silver</Badge>
                            </i>
                          </h5>
                        ) : client.profile === 'Gold' ? (
                          <h5>
                            <i>
                              <Badge color="warning">Gold</Badge>
                            </i>
                          </h5>
                        ) : (
                          <h5>
                            <i>
                              <Badge color="dark">Platinum</Badge>
                            </i>
                          </h5>
                        )}
                      </td>

                      <td>{client.pointsFidelite}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button
                            tag={Link}
                            to={`${match.url}/${client.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                            color="info"
                            size="sm"
                          >
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              ) : (
                !loading && (
                  <div className="alert alert-warning">
                    <Translate contentKey="elbazarApp.client.home.notFound">No Clients found</Translate>
                  </div>
                )
              )}
            </Table>
          </Col>
        </Row>
      </div>

      {props.totalItems ? (
        <div className={clientList && clientList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
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
    </div>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientList: client.entities,
  loading: client.loading,
  totalItems: client.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Client);
