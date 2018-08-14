package org.testcontainers.containers;

import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;

import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

public class ExasolDockerContainer<SELF extends ExasolDockerContainer<SELF>> extends
JdbcDatabaseContainer<SELF> {
  public static final String EXASOL_IMAGE = "exasol/docker-db";
  public static final Integer EXASOL_PORT = 8888;
  // wait for 5 minutes to startup
  public static final Integer EXASOL_STARTUP_TIME = 5 * 60;

  private String username = "sys";
  private String password = "exasol";

  public ExasolDockerContainer() {
    this(EXASOL_IMAGE + ":latest");
  }

  public ExasolDockerContainer(final String dockerImageName) {
    super(dockerImageName);
  }

  @Override
  protected void configure() {
    super.configure();
    addExposedPort(EXASOL_PORT);
    withPrivilegedMode(true);
    withStartupTimeout(Duration.of(EXASOL_STARTUP_TIME, SECONDS));
  }

  @Override
  public String getDriverClassName() {
    return "com.exasol.jdbc.EXADriver";
  }

  @Override
  public String getJdbcUrl() {
    return "jdbc:exa:" + getContainerIpAddress() + ":" +
      getMappedPort(EXASOL_PORT) + ";user=" + username + ";password=" + password;
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

}
