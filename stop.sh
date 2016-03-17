#!/bin/bash
kill $(ps aux | grep '[t]arget/NodeViz-0.0.1-SNAPSHOT.jar' | awk '{print $2}')
