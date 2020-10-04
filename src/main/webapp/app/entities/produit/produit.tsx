import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table, Badge } from 'reactstrap';
import {
  openFile,
  byteSize,
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
import { getSearchEntities, getEntities, getFilteredEntities } from './produit.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { TextField, FormControlLabel, Checkbox, MenuItem, Select } from '@material-ui/core';
import {
  handleFilterIDChange,
  handleFilterChangeChecked,
  getFilterRequestString,
  handleNumberSelectChanged,
  handleSelectValueChanged,
} from 'app/shared/util/entity-filter-services';

export interface IProduitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Produit = (props: IProduitProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [filter, setfilter] = useState('');

  const filterDefaultValue = {
    reference: { fieldName: 'reference', selecType: '.contains', inputValue: '' },
    nom: { fieldName: 'nom', selecType: '.contains', inputValue: '' },
    etat: { fieldName: 'etat', selecType: '.equals', inputValue: '' },
    stockMinimum: { fieldName: 'stockMinimum', selecType: '.equals', inputValue: '' },
    quantiteVenteMax: { fieldName: 'quantiteVenteMax', selecType: '.equals', inputValue: '' },
    horsStock: { fieldName: 'horsStock', selecType: '.equals', inputValue: '' },
    prixTTC: { fieldName: 'prixTTC', selecType: '.equals', inputValue: '' },
    sourceProduit: { fieldName: 'sourceProduit', selecType: '.equals', inputValue: '' },
    categorieNom: { fieldName: 'categorieNom', selecType: '.contains', inputValue: '' },
    sousCategorieNom: { fieldName: 'sousCategorieNom', selecType: '.contains', inputValue: '' },
    uniteCode: { fieldName: 'uniteCode', selecType: '.contains', inputValue: '' },
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

  const { produitList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="produit-heading">
        Liste des produits
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="elbazarApp.produit.home.createLabel">Create new Produit</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        <Table responsive hover size="sm">
          <thead>
            <tr>
              <th className="hand" style={{ whiteSpace: 'nowrap' }}>
                <div style={{ display: 'block' }} onClick={sort('reference')}>
                  <Translate contentKey="elbazarApp.produit.reference">Reference</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="reference"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.reference.inputValue}
                  />
                </div>
              </th>
              <th className="hand" style={{ textAlign: 'center' }}>
                <div style={{ display: 'block' }} onClick={sort('nom')}>
                  <Translate contentKey="elbazarApp.produit.nom">Nom</Translate> <FontAwesomeIcon icon="sort" />
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
              <th className="hand" style={{ whiteSpace: 'nowrap' }}>
                <div style={{ display: 'block' }} onClick={sort('etat')}>
                  <Translate contentKey="elbazarApp.produit.etat">Etat</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="etat"
                    value={filterValues.etat.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 80 }}
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

              <th className="hand" style={{ textAlign: 'center' }}>
                <div style={{ display: 'block' }} onClick={sort('stockMinimum')}>
                  Stock Min <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="stockMinimum"
                    value={filterValues.stockMinimum.selecType}
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
                    name="stockMinimum"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.stockMinimum.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('quantiteVenteMax')}>
                  Q.Vente Max en TND
                  <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="quantiteVenteMax"
                    value={filterValues.quantiteVenteMax.selecType}
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
                    name="quantiteVenteMax"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.quantiteVenteMax.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand" style={{ whiteSpace: 'nowrap' }}>
                <div style={{ display: 'block' }} onClick={sort('horsStock')}>
                  <Translate contentKey="elbazarApp.produit.horsStock">Hors Stock</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="horsStock"
                    value={filterValues.horsStock.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 120 }}
                  >
                    <MenuItem key={0} value={'false'}>
                      Disponible
                    </MenuItem>
                    <MenuItem key={1} value={'true'}>
                      Non Disponible
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th className="hand" style={{ whiteSpace: 'nowrap' }}>
                <div style={{ display: 'block' }} onClick={sort('prixTTC')}>
                  Prix TTC en TND <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="prixTTC"
                    value={filterValues.prixTTC.selecType}
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
                    name="prixTTC"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.prixTTC.inputValue}
                    style={{ width: 80 }}
                  />
                </div>
              </th>
              <th className="hand">
                <div style={{ display: 'block' }} onClick={sort('sourceProduit')}>
                  <Translate contentKey="elbazarApp.produit.sourceProduit">Source Produit</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <Select
                    id="comparator-select"
                    name="sourceProduit"
                    value={filterValues.sourceProduit.inputValue}
                    onChange={e => handleSelectValueChanged(e, filterValues, setfilterValues)}
                    style={{ width: 80 }}
                  >
                    <MenuItem key={0} value={'Locale'}>
                      Locale
                    </MenuItem>
                    <MenuItem key={1} value={'Externe'}>
                      Externe
                    </MenuItem>
                  </Select>
                </div>
              </th>
              <th className="hand" style={{ whiteSpace: 'nowrap' }}>
                <Translate contentKey="elbazarApp.produit.image">Image</Translate> <FontAwesomeIcon icon="sort" />
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  <Translate contentKey="elbazarApp.produit.categorie">Categorie</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="categorieNom"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.categorieNom.inputValue}
                  />
                </div>
              </th>
              <th>
                <div style={{ display: 'block' }}>
                  <Translate contentKey="elbazarApp.produit.sousCategorie">Sous Categorie</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="sousCategorieNom"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.sousCategorieNom.inputValue}
                  />
                </div>
              </th>
              <th style={{ whiteSpace: 'nowrap' }}>
                <div style={{ display: 'block' }}>
                  <Translate contentKey="elbazarApp.produit.unite">Unite</Translate> <FontAwesomeIcon icon="sort" />
                </div>
                <div style={{ display: 'block' }}>
                  <TextField
                    id="filter-id"
                    name="uniteCode"
                    onChange={e => handleFilterIDChange(e, filterValues, setfilterValues)}
                    value={filterValues.uniteCode.inputValue}
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
          {produitList && produitList.length > 0 ? (
            <tbody>
              {produitList.map((produit, i) => (
               
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${produit.id}`} color="link" size="sm">
                      {produit.reference}
                    </Button>
                  </td>
                  <td style={{ textAlign: 'center' }}>{produit.nom}</td>
                  <td>
                    {produit.etat ? (
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
                  <td style={{ textAlign: 'center' }}>{produit.stockMinimum}</td>
                  <td style={{ textAlign: 'center' }}>{produit.quantiteVenteMax}</td>
                  <td>{produit.horsStock ? 'Non Disponible' : 'Disponible'}</td>
                  <td style={{ textAlign: 'center' }}>{produit.prixTTC}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.SourcePrd.${produit.sourceProduit}`} />
                  </td>
                  <td>
                    {produit.imageUrl ? (
                      <div>
                        
                        
                            <img src={`media/${produit.imageUrl}`} style={{ maxHeight: '30px' }} />
                          
                      
                      </div>
                    ) : null}
                  </td>
                  <td>{produit.categorieNom ? <Link to={`categorie/${produit.categorieId}`}>{produit.categorieNom}</Link> : ''}</td>
                  <td>
                    {produit.sousCategorieNom ? (
                      <Link to={`sous-categorie/${produit.sousCategorieId}`}>{produit.sousCategorieNom}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{produit.uniteCode ? <Link to={`produit-unite/${produit.uniteId}`}>{produit.uniteCode}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${produit.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${produit.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="elbazarApp.produit.home.notFound">No Produits found</Translate>
              </div>
            )
          )}
        </Table>
      </div>
      {props.totalItems ? (
        <div className={produitList && produitList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ produit }: IRootState) => ({
  produitList: produit.entities,
  loading: produit.loading,
  totalItems: produit.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getFilteredEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Produit);
