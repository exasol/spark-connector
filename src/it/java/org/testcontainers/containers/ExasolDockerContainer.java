package org.testcontainers.containers;

import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;

import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

public class ExasolDockerContainer<SELF extends ExasolDockerContainer<SELF>> extends
JdbcDatabaseContainer<SELF> {
  public static final String EXASOL_IMAGE = "exasol/docker-db";
  public static final String EXASOL_VERSION = "6.0.13-d1";
  public static final String EXASOL_HOST = "192.168.0.2";
  public static final Integer EXASOL_PORT = 8888;
  // wait for 5 minutes to startup
  public static final Integer EXASOL_STARTUP_TIME = 15 * 60;

  private String username = "sys";
  private String password = "exasol";

  public ExasolDockerContainer() {
    this(EXASOL_IMAGE + ":" + EXASOL_VERSION);
  }

  public ExasolDockerContainer(final String dockerImageName) {
    super(dockerImageName);
  }

  @Override
  protected void configure() {
    super.configure();
    withNetworkMode("dockernet");
    withPrivilegedMode(true);
    withStartupTimeoutSeconds(EXASOL_STARTUP_TIME);
    withCreateContainerCmdModifier(cmd -> cmd.withIpv4Address(EXASOL_HOST));
  }

  @Override
  public String getDriverClassName() {
    return "com.exasol.jdbc.EXADriver";
  }

  @Override
  public String getJdbcUrl() {
    return "jdbc:exa:" + getHost() + ":" + EXASOL_PORT;
  }

  @Override
  public String getTestQueryString() {
    return "SELECT 1";
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public SELF withUsername(final String username) {
    this.username = username;
    return self();
  }

  @Override
  public SELF withPassword(final String password) {
    this.password = password;
    return self();
  }

  public String getHost() {
    return EXASOL_HOST;
  }

  public Integer getPort() {
    return EXASOL_PORT;
  }


}
