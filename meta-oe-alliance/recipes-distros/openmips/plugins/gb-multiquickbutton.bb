MODULE = "Gigablue MultiQuickButton"
DESCRIPTION = "Multi Quickbutton editor/wizard/code interpreter for keyboard and RC" 
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPLv2"

DEPENDS = "enigma2"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.7.11+git${SRCPV}"
PKGV = "2.7.11+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/openmips/MultiQuickButton.git;protocol=git"

S = "${WORKDIR}/git"

require conf/license/license-gplv2.inc

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/locale/*.po"

inherit autotools

EXTRA_OECONF = "\
	--with-libsdl=no --with-boxtype=${MACHINE} --with-po \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

# remove unused .pyc files
do_install_append() {
	find ${D}/usr/lib/enigma2/python/Plugins/Extensions/MultiQuickButton/ -name '*.pyc' -exec rm {} \;
	install -d ${D}/etc
	install -d ${D}/etc/MultiQuickButton
	mv -f ${D}/tmp/mqb/*.xml ${D}/etc/MultiQuickButton
	rm -rf /tmp/mqb
}



