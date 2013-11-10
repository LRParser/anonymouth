#!/bin/bash
ant dist && cp dist/lib/jsan.jar activator-1.0.0/JStyloService/lib/ && cp lib/*.jar activator-1.0.0/JStyloService/lib/

cp -r jsan_resources activator-1.0.0/JStyloService/
