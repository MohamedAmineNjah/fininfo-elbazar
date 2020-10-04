import React from 'react';
import MenuItem from '../../../shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/categorie">
      <Translate contentKey="global.menu.entities.categorie" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sous-categorie">
      <Translate contentKey="global.menu.entities.sousCategorie" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/produit">
      <Translate contentKey="global.menu.entities.produit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/produit-unite">
      <Translate contentKey="global.menu.entities.produitUnite" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/stock">
      <Translate contentKey="global.menu.entities.stock" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/mouvement-stock">
      <Translate contentKey="global.menu.entities.mouvementStock" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      <Translate contentKey="global.menu.entities.client" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adresse">
      <Translate contentKey="global.menu.entities.adresse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/gestion-fidelite">
      <Translate contentKey="global.menu.entities.gestionFidelite" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/commande">
      <Translate contentKey="global.menu.entities.commande" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/commande-lignes">
      <Translate contentKey="global.menu.entities.commandeLignes" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/zone">
      <Translate contentKey="global.menu.entities.zone" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/livraison">
      <Translate contentKey="global.menu.entities.livraison" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/affectation-zone">
      <Translate contentKey="global.menu.entities.affectationZone" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/info-societe">
      <Translate contentKey="global.menu.entities.infoSociete" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
