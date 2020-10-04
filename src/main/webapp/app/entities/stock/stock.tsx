import React, { useState, useEffect, Component } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps, Switch } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table, Badge, NavLink } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { TabContent, TabPane, Nav, NavItem, Card, CardTitle, CardText } from 'reactstrap';
import classnames from 'classnames';

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
import { getSearchEntities, getEntities, getFilteredEntities } from './stock.reducer';

import { IStock } from 'app/shared/model/stock.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ButtonGroup, TextField, Select, MenuItem, FormControlLabel, Checkbox } from '@material-ui/core';
import MouvementStock from '../mouvement-stock/mouvement-stock';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import { getFilterRequestString, handleFilterIDChange, handleNumberSelectChanged, handleFilterChangeChecked, handleSelectValueChanged } from 'app/shared/util/entity-filter-services';

export interface IStockProps extends Component, StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const Stock = (props: IStockProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const [filter, setfilter] = useState('');

  const filterDefaultValue = {
    refProduitReference: { fieldName: 'refProduitReference', selecType: '.contains', inputValue: '' },
    nomProduit: { fieldName: 'nomProduit', selecType: '.contains', inputValue: '' },
    stockReserve: { fieldName: 'stockReserve', selecType: '.equals', inputValue: '' },
    stockCommande: { fieldName: 'stockCommande', selecType: '.equals', inputValue: '' },
    stockDisponible: { fieldName: 'stockDisponible', selecType: '.equals', inputValue: '' },
    stockPhysique: { fieldName: 'stockPhysique', selecType: '.equals', inputValue: '' },
    stockMinimum: { fieldName: 'stockMinimum', selecType: '.equals', inputValue: '' },
    alerteStock: { fieldName: 'alerteStock', selecType: '.equals', inputValue: ''},
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

  const [activeTab, setActiveTab] = useState('1');

  const toggle = tab => {
    if (activeTab !== tab) setActiveTab(tab);
  };
  const { stockList, match, loading, totalItems } = props;
  return (
    <div>
      <div>
        <Nav tabs>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '1' })}
              onClick={() => {
                toggle('1');
              }}
              tag={Link}
              to="/stock"
            >
              Stocks
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '2' })}
              onClick={() => {
                toggle('2');
              }}
              tag={Link}
              to="/mouvement-stock"
            >
              Mouvement Stocks
            </NavLink>
          </NavItem>
        </Nav>
        <Switch>
          <TabContent activeTab={activeTab}>
            <TabPane tabId="1"></TabPane>
            <TabPane tabId="2">
              <ErrorBoundaryRoute path={`/mouvement-stock`} component={MouvementStock} />
            </TabPane>
          </TabContent>
        </Switch>
      </div>

      <h2 id="stock-heading"></h2>

      <div className="table-responsive">

        <Table responsive>
          <thead>
            <tr>
              <th>
                <div style={{ display: 'block' }}>
                  <Translate contentKey="elbazarApp.stock.refProduit">Ref Produit</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="refProduitReference"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.refProduitReference.inputValue}
                  />
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  Nom Produit
                  <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="nomProduit"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.nomProduit.inputValue}
                  />
                </div>
              </th>
              {/* Commander */}
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('stockReserve')}>
                  <Translate contentKey="elbazarApp.stock.stockReserve">Stock Reserve</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockReserve"
                    value={filterValues.stockReserve.selecType}
                    onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                    <MenuItem key={2} value={'.equals'}>=</MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="stockReserve"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockReserve.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              {/* Confirmer */}
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('stockCommande')}>
                  <Translate contentKey="elbazarApp.stock.stockCommande">Stock Commande</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockCommande"
                    value={filterValues.stockCommande.selecType}
                    onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                    <MenuItem key={2} value={'.equals'}>=</MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="stockCommande"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockCommande.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('stockDisponible')}>
                  <Translate contentKey="elbazarApp.stock.stockDisponible">Stock Disponible</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockDisponible"
                    value={filterValues.stockDisponible.selecType}
                    onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                    <MenuItem key={2} value={'.equals'}>=</MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="stockDisponible"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockDisponible.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              {/* Réel */}
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('stockPhysique')}>
                  <Translate contentKey="elbazarApp.stock.stockPhysique">Stock Physique</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockPhysique"
                    value={filterValues.stockPhysique.selecType}
                    onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                    <MenuItem key={2} value={'.equals'}>=</MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="stockPhysique"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockPhysique.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('stockMinimum')}>
                  <Translate contentKey="elbazarApp.stock.stockMinimum">Stock Minimum</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockMinimum"
                    value={filterValues.stockMinimum.selecType}
                    onChange={(e) => handleNumberSelectChanged(e, filterValues, setfilterValues)}
                  >
                    <MenuItem key={0} value={'.greaterThan'}>&gt;</MenuItem>
                    <MenuItem key={1} value={'.lessThan'}>&lt;</MenuItem>
                    <MenuItem key={2} value={'.equals'}>=</MenuItem>
                  </Select>
                  <TextField
                    id="filter-id"
                    name="stockMinimum"
                    onChange={(e) => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockMinimum.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand" >
                <div style={{ display: 'block' }} onClick={sort('alerteStock')}>
                  <Translate contentKey="elbazarApp.stock.alerteStock">Alerte Stock</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                <Select
                    id="comparator-select"
                    name="alerteStock"
                    value={filterValues.alerteStock.inputValue}
                    onChange={(e) => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 120 }}
                  >
                    <MenuItem key={0} value={'true'}>À commander</MenuItem>
                    <MenuItem key={1} value={'false'}>Disponible</MenuItem>
                  </Select>
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
          {stockList && stockList.length > 0 ? (
            <tbody>
              {stockList.map((stock, i) => (
                <tr key={`entity-${i}`}>
                  <td>{stock.refProduitReference ? <Link to={`produit/${stock.refProduitId}`}>{stock.refProduitReference}</Link> : ''}</td>
                  <td>{stock.nomProduit}</td>
                  <td>{stock.stockReserve}</td>
                  <td>{stock.stockCommande}</td>
                  <td>{stock.stockDisponible}</td>
                  <td>{stock.stockPhysique}</td>
                  <td>{stock.stockMinimum}</td>
                  <td>
                    {stock.alerteStock ? (
                      <h4>
                        <Badge color="primary">À commander</Badge>
                      </h4>
                    ) : (
                        <h4>
                          <Badge color="success">Disponible</Badge>
                        </h4>
                      )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container"></div>
                  </td>
                </tr>
              ))}
            </tbody>

          ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="elbazarApp.stock.home.notFound">No Stocks found</Translate>
                </div>
              )
            )}
        </Table>
      </div>
      {props.totalItems ? (
        <div className={stockList && stockList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ stock }: IRootState) => ({
  stockList: stock.entities,
  loading: stock.loading,
  totalItems: stock.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Stock);
