import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const AdminEntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="cube" to="/categorie">
      <Translate contentKey="global.menu.entities.categorie" />
    </MenuItem>
    <MenuItem icon="cubes" to="/sous-categorie">
      <Translate contentKey="global.menu.entities.sousCategorie" />
    </MenuItem>
    <MenuItem icon="flask" to="/produit">
      <Translate contentKey="global.menu.entities.produit" />
    </MenuItem>
    <MenuItem icon="balance-scale" to="/produit-unite">
      <Translate contentKey="global.menu.entities.produitUnite" />
    </MenuItem>
    <MenuItem icon="database" to="/stock">
      <Translate contentKey="global.menu.entities.stock" />
    </MenuItem>
    <MenuItem icon="users" to="/client">
      <Translate contentKey="global.menu.entities.client" />
    </MenuItem>
    <MenuItem icon="trophy" to="/gestion-fidelite">
      <Translate contentKey="global.menu.entities.gestionFidelite" />
    </MenuItem>
    <MenuItem icon="list-alt" to="/commande">
      <Translate contentKey="global.menu.entities.commande" />
    </MenuItem>
    <MenuItem icon="map-marker" to="/zone">
      <Translate contentKey="global.menu.entities.zone" />
    </MenuItem>
    <MenuItem icon="truck" to="/livraison">
      <Translate contentKey="global.menu.entities.livraison" />
    </MenuItem>
    <MenuItem icon="map" to="/affectation-zone">
      <Translate contentKey="global.menu.entities.affectationZone" />
    </MenuItem>
    <MenuItem icon="id-card" to="/info-societe">
      <Translate contentKey="global.menu.entities.infoSociete" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/slides">
      <Translate contentKey="global.menu.entities.slides" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
