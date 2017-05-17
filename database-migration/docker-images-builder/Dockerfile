FROM dhoer/flyway

RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    apt-utils \
 && rm -rf /var/lib/apt/lists/*



# Install jq
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    jq \
 && rm -rf /var/lib/apt/lists/*

COPY flyway-entrypoint.sh /usr/local/bin/
RUN ln -s usr/local/bin/flyway-entrypoint.sh # backwards compat
RUN chmod +x /usr/local/bin/flyway-entrypoint.sh

WORKDIR /flyway
ENV VAULT_TOKEN ''
ENV VAULT_CONNECT ''
ENV VAULT_DB_PATH ''
ENV VAULT_DB_ROLE ''
ENV DB_URL ''

ENTRYPOINT ["flyway-entrypoint.sh"]
CMD ["--help"]