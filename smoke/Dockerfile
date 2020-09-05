FROM ubuntu:focal

RUN ln -fs /usr/share/zoneinfo/UCT /etc/localtime && \
    apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends dovecot-core dovecot-pop3d && \
    useradd -m alice && \
    echo 'alice:Rabbit' | chpasswd

COPY conf/* /etc/dovecot/conf.d/
COPY alice.mbox /var/mail/alice

CMD ["/usr/sbin/dovecot", "-F"]