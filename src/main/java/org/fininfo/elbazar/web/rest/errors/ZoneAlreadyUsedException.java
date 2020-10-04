package org.fininfo.elbazar.web.rest.errors;

public class ZoneAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ZoneAlreadyUsedException() {
        super(ErrorConstants.ZONE_ALREADY_USED_TYPE, "Zone code already used!", "userManagement", "zoneexists");
    }
}
