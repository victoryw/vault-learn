FROM dhoer/flyway

RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    apt-utils \
 && rm -rf /var/lib/apt/lists/*



# Install jq
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    jq \
 && rm -rf /var/lib/apt/lists/*

COPY flyway-entrypoint.sh /usr/local/bin/flyway-entrypoint.sh

WORKDIR /flyway

#ENTRYPOINT ["flyway-entrypoint.sh"]
#CMD ["--help"]