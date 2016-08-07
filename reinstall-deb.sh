#!/bin/sh

sudo apt-get purge lodtenant-cli lodtenant-common
sudo dpkg -i `find lodtenant-debian-common -name 'lodtenant-common_*.deb'`
sudo dpkg -i `find lodtenant-debian-cli '*-debian*/target/' -name 'lodtenant-cli_*.deb'`

