DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.12.4"
PR = "r1"

SRCDATE = "1211"
SRCDATE_ebox7358 = "1227"
MACHINE_KERNEL_PR_append = ".2"

SRC_URI[ebox5100.md5sum] = "2c6957a0f3662910e53df8eb31705e01"
SRC_URI[ebox5100.sha256sum] = "2ba88a551a5c3a7095a2000e03841895d7b3892116d379d1009a8de9f5820f89"
SRC_URI[eboxlumi.md5sum] = "2c6957a0f3662910e53df8eb31705e01"
SRC_URI[eboxlumi.sha256sum] = "2ba88a551a5c3a7095a2000e03841895d7b3892116d379d1009a8de9f5820f89"
SRC_URI[ebox5000.md5sum] = "bafb17f5e8dd1c4935d2ec213b2d62e1"
SRC_URI[ebox5000.sha256sum] = "36b844b07e5f38b3b597bc55bd182438f9fd3b3c52d234914f6aabf031e31be9"
SRC_URI[ebox7358.md5sum] = "d7a5d669baa035aa351b3a27a00165e7"
SRC_URI[ebox7358.sha256sum] = "37fe31cf521a97ededd542af3d10ef2431a17ecd9c336ff48a395405517b94af"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.mixos-support.com/${MACHINE}-linux-${KV}_${SRCDATE}.tar.bz2;name=${MACHINE} \
	file://defconfig \
	"

S = "${WORKDIR}/linux-${KV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
	oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
	oe_runmake oldconfig
}

kernel_do_install_append() {
	${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/mtd2 0 0
			nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}


